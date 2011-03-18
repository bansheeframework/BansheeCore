package com.bansheeproject.engine;

import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.engine.soap.SOAPVersion;


/**
 * It is an extension to invocation context, taking into account
 * that the context is SOAP based.
 *  
 * @author Alexandre Saudate
 * @since 1.0
 */
public class SOAPInvocationContext extends InvocationContext {

	public SOAPInvocationContext(WebServicesInvocationData invocationData,
			BansheeDispatcher dispatcher, String requestData, SOAPVersion version) {
		super(invocationData, dispatcher, requestData);
		if (dispatcher instanceof JAXWSDispatcher ) {
			((JAXWSDispatcher)dispatcher).setSOAPVersion(version);
		}
	}

}
