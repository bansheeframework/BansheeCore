package com.bansheeproject.engine;

import java.io.IOException;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.soap.SOAPFaultException;

import com.bansheeproject.engine.soap.SOAPUtils;
import com.bansheeproject.engine.soap.SOAPVersion;
import com.bansheeproject.exceptions.BansheeInvocationFaultException;
import com.bansheeproject.exceptions.ServiceInvokeException;

/**
 * 
 * A dispatcher for JAX-WS based web services.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public abstract class JAXWSDispatcher extends BansheeDispatcher {

	
	
	public abstract Dispatch getDelegate() ;
	
	private SOAPVersion soapVersion;
	
	public void setSOAPVersion (SOAPVersion soapVersion) {
		this.soapVersion = soapVersion;
	}
	
	@Override
	public String invoke(String param) throws ServiceInvokeException, BansheeInvocationFaultException{
		
		
			
			SOAPMessage soapMessage;
			try {
				soapMessage = SOAPUtils.buildMessage(param, soapVersion);
			} catch (IOException e) {
				throw new ServiceInvokeException(e);
			} catch (SOAPException e) {
				throw new ServiceInvokeException(e);
			}
			
			SOAPMessage response;
			try {
				response = (SOAPMessage)getDelegate().invoke(soapMessage);
			}
			catch (SOAPFaultException e) {
				try {
					
					
					throw new BansheeInvocationFaultException(SOAPUtils.getFaultData(e, soapVersion));
				} catch (Exception e1) {
					if (e1 instanceof BansheeInvocationFaultException) 
						throw (BansheeInvocationFaultException)e1;
					throw new ServiceInvokeException(e1);
				}
			}
			String responseData = SOAPUtils.getMessageContent(response);
			
			return  responseData;
		
		
	}


	@Override
	public void invokeOneWay(String param) throws ServiceInvokeException, BansheeInvocationFaultException {
		
		
		try {
			
			SOAPMessage soapMessage = SOAPUtils.buildMessage(param, soapVersion);
			getDelegate().invokeOneWay(soapMessage);
		}
		catch (SOAPFaultException e) {
			try {
				throw new BansheeInvocationFaultException(SOAPUtils.getFaultData(e, soapVersion));
			} catch (Exception e1) {
				throw new ServiceInvokeException(e1);
			}
		}
		catch (Exception ex) {
			if (ex instanceof BansheeInvocationFaultException) {
				throw (BansheeInvocationFaultException)ex;
			}
			throw new ServiceInvokeException(ex);
		}
		
		
	}

	
	
}
