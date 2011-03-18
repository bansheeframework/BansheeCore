package com.bansheeproject;



import com.bansheeproject.engine.BansheeDispatcher;
import com.bansheeproject.engine.InvocationContext;
import com.bansheeproject.engine.SOAPInvocationContext;
import com.bansheeproject.engine.soap.SOAPVersion;
import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;

/**
 * Represents an implementation of service invocation using
 * SOAP 1.1.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class SOAP11InvocationData extends SOAPInvocationData {

	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(SOAP11InvocationData.class);
	
	public SOAP11InvocationData(ServiceData serviceData) {
		super(serviceData);
		logger.debug(new StringBuilder("Instantiating SOAP 1.1 invocation data with service data ").append(serviceData));
	}

	@Override
	public SOAPVersion getVersion() {
		
		return SOAPVersion.ONEDOTONE;
	}

	@Override
	protected InvocationContext buildInvocationContext(
			BansheeDispatcher dispatcher, String requestObject) {
		logger.debug("Building SOAP invocation context.. ");
		return new SOAPInvocationContext(this, dispatcher, requestObject, SOAPVersion.ONEDOTONE);
	}

	

}
