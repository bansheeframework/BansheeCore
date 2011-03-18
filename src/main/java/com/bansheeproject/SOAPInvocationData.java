package com.bansheeproject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.slf4j.Logger;

import com.bansheeproject.engine.ExceptionParameter;
import com.bansheeproject.engine.ObjectParameter;
import com.bansheeproject.engine.SOAPResponse;
import com.bansheeproject.engine.ServiceParameter;
import com.bansheeproject.engine.StringParameter;
import com.bansheeproject.engine.soap.SOAPUtils;
import com.bansheeproject.engine.soap.SOAPVersion;
import com.bansheeproject.exceptions.BansheeInvocationFaultException;
import com.bansheeproject.exceptions.ConverterException;
import com.bansheeproject.exceptions.InvocationException;
import com.bansheeproject.features.LogFeature;
import com.bansheeproject.features.PasswordType;
import com.bansheeproject.features.SSLAliasSelectorFeature;
import com.bansheeproject.features.WSDLRealAddressFeature;
import com.bansheeproject.features.WSSecurityUsernamePassword;
import com.bansheeproject.features.WSTimestampFeature;
import com.bansheeproject.features.security.CertificateCallback;
import com.bansheeproject.features.security.DefaultCertificateCallback;
import com.bansheeproject.log.BansheeLogger;
import com.bansheeproject.log.ConsoleLogger;
import com.bansheeproject.utils.TimeUnit;

/**
 * Represents an implementation of service invocation using
 * SOAP
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public abstract class SOAPInvocationData extends WebServicesInvocationData{

	private List<ServiceParameter> headers;
	
	

	public SOAPInvocationData(ServiceData serviceData) {
		super(serviceData);
		headers = new ArrayList<ServiceParameter>();
	}


	public void addHeader(ServiceParameter parameter) {
		headers.add(parameter);
	}
	
	public void addHeaderString (String header) {
		headers.add(new StringParameter(header));
	}
	
	public void addHeaderObject (Object header) {
		headers.add(new ObjectParameter(header, null, getObjectConverter()));
	}
	
	
	public void clearHeaders(){
		headers.clear();
	}


	public List<ServiceParameter> listHeaders() {
		return new ArrayList<ServiceParameter>(headers);
	}
	
	
	public void addSecurityLayer (String reference, InputStream keyStore,
			String keyStorePassword,
			InputStream trustStore,
			String trustStorePassword) {
		
		addSecurityLayer(reference, new DefaultCertificateCallback(), keyStore, keyStorePassword, trustStore, trustStorePassword);
	}
	
	public void addSecurityLayer(String reference, CertificateCallback callback, InputStream keyStore,
			String keyStorePassword,
			InputStream trustStore,
			String trustStorePassword) {
		addFeature(new SSLAliasSelectorFeature(reference, callback, keyStore, keyStorePassword, trustStore, trustStorePassword));
	}
	
	
	
	
	public void overwriteInvocationAddress (String address) {
		addFeature(new WSDLRealAddressFeature(address));
	}
	
	public void addWSSecurityTimestamp(long modifier, TimeUnit unit) {
		addFeature(new WSTimestampFeature(modifier, unit));
	}

	public void addWSSecurityTimestamp(long modifier, TimeUnit unit, String timestampId) {
		addFeature(new WSTimestampFeature(modifier, unit, timestampId));
	}

	public void addWSSecurityTimestamp(long modifier, TimeUnit unit, String timestampId, boolean mustUnderstand) {
		addFeature(new WSTimestampFeature(modifier, unit, timestampId, mustUnderstand));
	}

	public void addWSSecurityUsernamePassword (String userName, String password) {
		addFeature(new WSSecurityUsernamePassword(userName, password, getVersion()));
	}

	public void addWSSecurityUsernamePassword (String userName, String password, PasswordType passwordType) {
		addFeature(new WSSecurityUsernamePassword(userName, password, passwordType, getVersion()));
	}

	public void addWSSecurityUsernamePassword (String userName, String password, PasswordType passwordType, boolean mustUnderstand) {
		addFeature(new WSSecurityUsernamePassword(userName, password, passwordType, mustUnderstand, getVersion()));
	}
	
	public void logOperations (BansheeLogger logger) {
		addFeature(new LogFeature(logger) );
	}
	
	public void logOperations (Logger logger) {
		addFeature(new LogFeature(logger));
	}
	
	public void logOperationsToConsole() {
		addFeature(new LogFeature(new ConsoleLogger()));
	}
	
	

	



	public abstract SOAPVersion getVersion();
	
	protected String buildRequestObject() {

		if (getServiceParameter() == null) {
			throw new IllegalStateException("Service parameter cannot be null.");
		}

		String messageBody = getServiceParameter().encode();
		List<String> messageHeaders = new ArrayList<String>();
		for (ServiceParameter header : listHeaders()) {
			messageHeaders.add(header.encode());
		}

		String buildEnvelope = SOAPUtils.buildEnvelope(messageBody, getVersion(), messageHeaders.toArray(new String[]{}));

		return buildEnvelope;
	}
	
	protected Response buildResponseObject(String responseData) {
		String bodyContent = null;
		try {
			SOAPMessage soapMessage = (SOAPMessage)SOAPUtils.buildMessage(responseData, getVersion());
			
			bodyContent = SOAPUtils.getMessageBodyContent(soapMessage);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (SOAPException e) {
			throw new RuntimeException(e);
		}
		
		ServiceParameter response = getServiceParameter().resolveResponse(bodyContent);
		
		SOAPResponse soapMessageResponse = new SOAPResponse(response);
		return soapMessageResponse;
	}
	
	protected Response buildExceptionResponse (Exception ex) throws Exception {
		if (ex instanceof BansheeInvocationFaultException) {
			BansheeInvocationFaultException invocationFaultException = (BansheeInvocationFaultException)ex;
			if (!isHandleExceptionAsString()) {
				Collection<ExceptionParameter> exceptionParameters = listExceptionParameters();
				String faultData = invocationFaultException.getResponseData();
				for (ExceptionParameter param : exceptionParameters) {
					
					
					try {
						ServiceParameter responseParameter = param.resolveResponse(SOAPUtils.getFaultData(faultData, getVersion()));
						if (responseParameter != null) {							
							return new ExceptionResponse(new InvocationException(responseParameter.decode()));
						}
					}
					catch (ConverterException converterException) {
						//Do nothing, as multiple exception types may raise this kind of condition
					}
					
				}
				
			}
			else {
				String reason = invocationFaultException.getResponseData();
				return new ExceptionResponse(new InvocationException(reason));
			}
			throw ex;
		}
		
		
		throw ex;
	}
	
	

}
