package com.bansheeproject.engine.wsdl;

import com.bansheeproject.exceptions.WSDLRetrievalException;


/**
 * It is responsible for retrieving {@link WSDLData}.
 * @author Alexandre Saudate
 * @since 1.0
 */
public interface WSDLRetriever {

	
	public WSDLData retrieveData(String address) throws WSDLRetrievalException;
	
	
	
}
