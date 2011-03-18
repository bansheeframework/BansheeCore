package com.bansheeproject;

import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;


/**
 * Represents the response of any service invocation. It 
 * delegates to its subtypes the responsability for 
 * answering to any kind of data.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 *
 */
public abstract class Response {

	public abstract Object getResponseData();
	
	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(Response.class);
	
	public boolean isExceptionResponse() {
		logger.debug("Checking if response is exception...");
		boolean response = (this instanceof ExceptionResponse) ;
		logger.debug(new StringBuilder("Responding ").append(response));
		return response;
	}
	
	
	public Class<?> getResponseType() {
		logger.debug("Invoking getResponseType()..");
		if (getResponseData() == null) {
			logger.debug("Returning void.class");
			return void.class;
		}
		logger.debug("Returning getResponseData().getClass()");
		return getResponseData().getClass();
	}
	
	@Override
	public String toString() {
		if (getResponseData() != null  ) {
			return getResponseData().toString();
		}
		return super.toString();
	}
}
