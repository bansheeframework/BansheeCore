package com.bansheeproject.exceptions;


/**
 * 
 * Thrown when a given converter could not be initialized.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class ConverterException extends BansheeUncheckedException {

	public ConverterException() {
	}

	public ConverterException(String message) {
		super(message);
	}

	public ConverterException(Throwable cause) {
		super(cause);
	}

	public ConverterException(String message, Throwable cause) {
		super(message, cause);
	}

}
