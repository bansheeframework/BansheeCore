package com.bansheeproject;

import com.bansheeproject.engine.BansheeDispatcher;
import com.bansheeproject.engine.InvocationContext;
import com.bansheeproject.engine.SOAPInvocationContext;
import com.bansheeproject.engine.soap.SOAPVersion;
import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;

/**
 * Represents an implementation of service invocation using
 * SOAP 1.2
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class SOAP12InvocationData extends SOAPInvocationData {

	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(SOAP12InvocationData.class);
	
	
	public SOAP12InvocationData(ServiceData serviceData) {
		super(serviceData);
		logger.debug("Instantiating a SOAP12InvocationData");

	}

	@Override
	public SOAPVersion getVersion() {

		return SOAPVersion.ONEDOTTWO;
	}

	@Override
	protected InvocationContext buildInvocationContext(
			BansheeDispatcher dispatcher, String requestObject) {
		logger.debug("Building invocation context of SOAPInvocationContext");
		return new SOAPInvocationContext(this, dispatcher, requestObject, SOAPVersion.ONEDOTTWO );
	}

	

}
