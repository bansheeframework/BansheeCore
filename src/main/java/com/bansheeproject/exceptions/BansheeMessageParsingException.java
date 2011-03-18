package com.bansheeproject.exceptions;


/**
 * Thrown when a given message could not be parsed.
 * @author Alexandre Saudate
 * @since 1.0
 */
public class BansheeMessageParsingException extends BansheeUncheckedException {

	public BansheeMessageParsingException() {
		
	}

	public BansheeMessageParsingException(String message) {
		super(message);

	}

	public BansheeMessageParsingException(Throwable cause) {
		super(cause);

	}

	public BansheeMessageParsingException(String message, Throwable cause) {
		super(message, cause);

	}

}
