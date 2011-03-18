package com.bansheeproject.exceptions;

/**
 * Thrown when a HTTP response is not available.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class HttpResponseNotAvailableException extends
		BansheeUncheckedException {

	private int responseCode;
	
	public HttpResponseNotAvailableException() {

	}

	public HttpResponseNotAvailableException(String message) {
		super(message);
	}

	public HttpResponseNotAvailableException(Throwable cause) {
		super(cause);
	}

	public HttpResponseNotAvailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

}
