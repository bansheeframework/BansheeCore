package com.bansheeproject.exceptions;


/**
 * Thrown when an invocation exception could not be handled.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class BansheeInvocationFaultException extends BansheeCheckedException {

	private String responseData;
	
	
	public BansheeInvocationFaultException(String responseData) {
		this.responseData = responseData;
	}

	public String getResponseData() {
		return responseData;
	}
	
	@Override
	public String getMessage() {
		
		return responseData;
	}
	
}
