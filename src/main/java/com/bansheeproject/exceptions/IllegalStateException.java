package com.bansheeproject.exceptions;


/**
 * Generic exception, thrown when a given component is not in
 * a valid state for some operation.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class IllegalStateException extends BansheeUncheckedException {

	public IllegalStateException() {
		
	}

	public IllegalStateException(String message) {
		super(message);

	}

	public IllegalStateException(Throwable cause) {
		super(cause);

	}

	public IllegalStateException(String message, Throwable cause) {
		super(message, cause);

	}

}
