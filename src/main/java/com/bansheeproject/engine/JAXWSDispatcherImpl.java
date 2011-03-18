package com.bansheeproject.engine;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Dispatch;

import com.bansheeproject.exceptions.BansheeInvocationFaultException;
import com.bansheeproject.exceptions.ServiceInvokeException;
import com.bansheeproject.features.security.CertificateCallback;
import com.bansheeproject.features.security.DefaultCertificateCallback;
import com.bansheeproject.features.security.SSLSocketFactoryGenerator;
import com.bansheeproject.log.BansheeLogger;
import com.sun.xml.internal.ws.client.BindingProviderProperties;


/**
 * A simple extension for {@link JAXWSDispatcher}. It is 
 * more intimate to the JAX-WS API.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class JAXWSDispatcherImpl extends JAXWSDispatcher {

	private Dispatch delegate;
		
	public JAXWSDispatcherImpl(Dispatch delegate) {
		this.delegate = delegate;
		
	}
	
	@Override
	public Dispatch getDelegate() {
		
		return delegate;
	}

	@Override
	public void setTimeout(long timeout) {
		
		
		delegate.getRequestContext().put(BindingProviderProperties.REQUEST_TIMEOUT, timeout);
		
	}

	@Override
	public void setCertificateSelectors(String reference, InputStream keyStoreData,
			String keyStorePassword, InputStream trustStoreData,
			String trustStorePassword) throws GeneralSecurityException, IOException {
		
		setCertificateSelectors(reference, keyStoreData, keyStorePassword, trustStoreData, trustStorePassword, new DefaultCertificateCallback());
		
		
	}

	
	
	@Override
	public void setCertificateSelectors(String reference, InputStream keyStoreData,
			String keyStorePassword, InputStream trustStoreData,
			String trustStorePassword, CertificateCallback callback) throws GeneralSecurityException, IOException {
		SSLSocketFactoryGenerator generator  = new SSLSocketFactoryGenerator();
		SSLSocketFactory socketFactory = generator.getSSLSocketFactoryInstance(reference, keyStoreData, keyStorePassword, trustStoreData, trustStorePassword, callback);
		
		getDelegate().getRequestContext().put("com.sun.xml.ws.transport.https.client.SSLSocketFactory", socketFactory);
		
	}


	@Override
	public void changeInvocationAddress(String address) {
		getDelegate().getRequestContext().put(Dispatch.ENDPOINT_ADDRESS_PROPERTY, address);		
	}
	

	
	
	@Override
	public String invoke(String param) throws ServiceInvokeException,
			BansheeInvocationFaultException {
	
		BansheeLogger logger = getLogger();

		logger.info(param);
		try {
			String response = super.invoke(param);
			logger.info(response);
			return response;
		}
		catch (BansheeInvocationFaultException ex) {
			logger.error(ex.getResponseData());
			throw ex;
		}

		
		
		
	}
	
	
	
}
