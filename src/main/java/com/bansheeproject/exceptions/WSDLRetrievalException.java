package com.bansheeproject.exceptions;


/**
 * Thrown when a WSDL could not be retrieved.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class WSDLRetrievalException extends BansheeCheckedException {

	public WSDLRetrievalException() {
		
	}

	public WSDLRetrievalException(String message) {
		super(message);
	}

	public WSDLRetrievalException(Throwable cause) {
		super(cause);
	}

	public WSDLRetrievalException(String message, Throwable cause) {
		super(message, cause);
	}

}
