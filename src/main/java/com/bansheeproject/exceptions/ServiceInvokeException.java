package com.bansheeproject.exceptions;


/**
 * Thrown when an exception could not be handled within an 
 * invocation.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class ServiceInvokeException extends BansheeCheckedException {


	
	public ServiceInvokeException() {
	
	}

	public ServiceInvokeException(String message) {
		super(message);
	}
	
	

	public ServiceInvokeException(Throwable cause) {
		super(cause);
		
	}
		
	

	public ServiceInvokeException(String message, Throwable cause) {
		super(message, cause);
	}

	
	
}
