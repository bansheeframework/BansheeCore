package com.bansheeproject.exceptions;


/**
 * Root exception for all unchecked exceptions.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class BansheeUncheckedException extends RuntimeException {

	public BansheeUncheckedException() {
	
	}

	public BansheeUncheckedException(String message) {
		super(message);
	
	}

	public BansheeUncheckedException(Throwable cause) {
		super(cause);
	
	}

	public BansheeUncheckedException(String message, Throwable cause) {
		super(message, cause);
	
	}

}
