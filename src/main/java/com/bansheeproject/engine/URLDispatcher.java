package com.bansheeproject.engine;


import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.bansheeproject.HttpHeader;
import com.bansheeproject.HttpMethod;
import com.bansheeproject.exceptions.BansheeInvocationFaultException;
import com.bansheeproject.exceptions.ServiceInvokeException;
import com.bansheeproject.features.security.CertificateCallback;
import com.bansheeproject.features.security.DefaultCertificateCallback;
import com.bansheeproject.features.security.SSLSocketFactoryGenerator;
import com.bansheeproject.log.BansheeLogger;
import com.bansheeproject.utils.IOUtils;


/**
 * It is a dispatcher based on HTTP.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class URLDispatcher extends BansheeDispatcher {

	private String address;
	
	private HttpMethod method;
	
	private List<HttpHeader> headers = new ArrayList<HttpHeader>();
	
	private HttpClient client;
	
	private int responseCode = -1;
	
	private String responseType = null;
	
	public URLDispatcher(String address, HttpMethod method, HttpHeader... headers) {
		this.address = address;
		this.method = method;
		this.client = new HttpClient();
		if (headers != null) {
			for (HttpHeader header : headers) {
				this.headers.add(header);
			}
		}
	}
	
	
	@Override
	public String invoke(String param) throws ServiceInvokeException, BansheeInvocationFaultException {		
		org.apache.commons.httpclient.HttpMethod method = getMethod(param, address);
		try {
			BansheeLogger logger = getLogger();
			
			logger.info(param);
			int responseCode = client.executeMethod(method);
			
			String httpResponse = IOUtils.getStringFromStream(method.getResponseBodyAsStream());
			//String httpResponse = new String(method.getResponseBody());
			this.responseCode = responseCode;
			this.responseType = method.getResponseHeader("Content-type").getValue();
			boolean faultInvocation = String.valueOf(responseCode).charAt(0) == '5';
			if (faultInvocation) {
				logger.error(httpResponse);
				throw new BansheeInvocationFaultException(httpResponse);
			}
			
			logger.info(httpResponse);
			return httpResponse;
		
		}
		catch (Exception e) {
			if (e instanceof BansheeInvocationFaultException) {
				throw (BansheeInvocationFaultException)e;
			}
			throw new ServiceInvokeException(e);
			
		}
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	
	public String getResponseType() {
		return responseType;
	}

	@Override
	public void invokeOneWay(String param) throws ServiceInvokeException {

		org.apache.commons.httpclient.HttpMethod method = getMethod(param, address);
		
		try {			
			BansheeLogger logger = getLogger();
			logger.info(param);
			
			int responseCode = client.executeMethod(method);
			
			this.responseCode = responseCode;
			String httpResponse = IOUtils.getStringFromStream(method.getResponseBodyAsStream());			
			boolean faultInvocation = String.valueOf(responseCode).charAt(0) == '5';
			if (faultInvocation) {
				logger.error(httpResponse);
				throw new BansheeInvocationFaultException(httpResponse);
			}
			
			logger.info(httpResponse);
			
		} catch (Exception e) {
			throw new ServiceInvokeException(e);
			
		}
		

	}
	
	
	
	public HttpClient getClient() {
		return client;
	}


	public String getAddress() {
		return address;
	}


	private org.apache.commons.httpclient.HttpMethod getMethod (String requestBody, String address) {
		if (this.method.equals(HttpMethod.GET)) {
			
			GetMethod get = new GetMethod(address);
			
			
			return get;
		}
		else if (this.method.equals(HttpMethod.POST)) {
			
			PostMethod post = new PostMethod(address);
			if (requestBody != null) {
				post.setRequestBody(requestBody);
				
			}
			
			
			
			return post;
		}
		else if (this.method.equals(HttpMethod.PUT)) {
			
			PutMethod put = new PutMethod(address);
			
			if (requestBody !=  null) {
				put.setRequestBody(requestBody);
			}
			return put;
		}
		else if (this.method.equals(HttpMethod.DELETE)) {
			
			DeleteMethod delete = new DeleteMethod(address);
			
			return delete;
		}
		else if (this.method.equals(HttpMethod.HEAD)) {
			
			HeadMethod head = new HeadMethod(address);
			
			
			
			
			return head;
		}
		else if (this.method.equals(HttpMethod.OPTIONS)) {
			
			OptionsMethod options = new OptionsMethod(address);
			
			return options;
		}
		
		
		return null;
	}

	

	@Override
	public void setTimeout(long timeout) {
		
		
	}


	@Override
	public void setCertificateSelectors(String reference, InputStream keyStoreData,
			String keyStorePassword, InputStream trustStoreData,
			String trustStorePassword) throws GeneralSecurityException,
			IOException {
				
		setCertificateSelectors(reference, keyStoreData, keyStorePassword, trustStoreData, trustStorePassword, new DefaultCertificateCallback());
		
		
	}


	@Override
	public void setCertificateSelectors(String reference, InputStream keyStoreData,
			String keyStorePassword, InputStream trustStoreData,
			String trustStorePassword, CertificateCallback callback)
			throws GeneralSecurityException, IOException {

		SSLSocketFactoryGenerator generator  = new SSLSocketFactoryGenerator();
		SSLSocketFactory socketFactory = generator.getSSLSocketFactoryInstance(reference, keyStoreData, keyStorePassword, trustStoreData, trustStorePassword, callback);		
		SSLProtocolSocketfactory protocolSocketfactory = new SSLProtocolSocketfactory(socketFactory);
		Protocol protocol = new Protocol("https", protocolSocketfactory, 443);
		Protocol.registerProtocol("https", protocol);
		
	}
	
	
	
	@Override
	public void changeInvocationAddress(String address) {
		this.address = address;
		
	}
	
	
	
	
	@Override
	public String toString() {
		
		ToStringBuilder builder = new ToStringBuilder(this);
		builder = builder.append(this.address).append(this.responseCode);
		builder = builder.append(this.responseType);	

		return builder.toString();
	}


	


	private class SSLProtocolSocketfactory implements ProtocolSocketFactory {

		private SSLSocketFactory delegateSocketFactory;
		
		public SSLProtocolSocketfactory(SSLSocketFactory delegate) {
			this.delegateSocketFactory = delegate;
		}
		
		
		public Socket createSocket(String arg0, int arg1) throws IOException,
				UnknownHostException {
			return delegateSocketFactory.createSocket(arg0, arg1);
		}

		public Socket createSocket(String arg0, int arg1, InetAddress arg2,
				int arg3) throws IOException, UnknownHostException {

			return delegateSocketFactory.createSocket(arg0, arg1, arg2, arg3);
		}

		public Socket createSocket(String arg0, int arg1, InetAddress arg2,
				int arg3, HttpConnectionParams arg4) throws IOException,
				UnknownHostException, ConnectTimeoutException {

			return delegateSocketFactory.createSocket(arg0, arg1, arg2, arg3);
		}		
		
	}

}
