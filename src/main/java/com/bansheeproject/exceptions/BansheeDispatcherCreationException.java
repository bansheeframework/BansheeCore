package com.bansheeproject.exceptions;


/**
 * Thrown when a given dispatcher cannot be created.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class BansheeDispatcherCreationException extends
		BansheeUncheckedException {

	public BansheeDispatcherCreationException() {
		
	}

	public BansheeDispatcherCreationException(String message) {
		super(message);

	}

	public BansheeDispatcherCreationException(Throwable cause) {
		super(cause);

	}

	public BansheeDispatcherCreationException(String message, Throwable cause) {
		super(message, cause);

	}

}
