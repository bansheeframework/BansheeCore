package com.bansheeproject.exceptions;


/**
 * Root exception for all checked exceptions of the system.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class BansheeCheckedException extends Exception {

	public BansheeCheckedException() {
		
	}

	public BansheeCheckedException(String message) {
		super(message);
		
	}

	public BansheeCheckedException(Throwable cause) {
		super(cause);
		
	}

	public BansheeCheckedException(String message, Throwable cause) {
		super(message, cause);
	
	}

}
