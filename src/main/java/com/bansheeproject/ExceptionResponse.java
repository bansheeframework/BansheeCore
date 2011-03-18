package com.bansheeproject;

import com.bansheeproject.exceptions.InvocationException;
import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;



/**
 * It is the type of response that is returned when an exception 
 * has been thrown by the service and the invocation is configured
 * to capture a given type of exception.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 *
 */
public class ExceptionResponse extends Response{
	
	private Exception exception;
	
	public ExceptionResponse(Exception exception) {
		this.exception = exception;
	}
	
	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(ExceptionResponse.class);
	
	@Override
	public Object getResponseData() {
		logger.debug("Trying to get exception response data...");
		if (exception instanceof InvocationException) {
			logger.debug("The exception stored is an invocation exception. Retrieving reason...");
			InvocationException invocationException = (InvocationException)exception;
			return invocationException.getReason();
		}
		logger.debug(new StringBuilder("Exception type is not an invocation exception. Returning the exception: ").append(exception).toString());
		return exception;
	}

}
