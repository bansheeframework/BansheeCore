package com.bansheeproject.exceptions;


/**
 * It is the default shell for exception responses.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class InvocationException extends Exception{
	
	
	public InvocationException(Object reason) {
		super();
		setReason(reason);
	}


	public InvocationException(Object reason, String arg0) {
		super(arg0);
		setReason(reason);
	}

	private Object reason;

	public Object getReason() {
		return reason;
	}

	public void setReason(Object reason) {
		this.reason = reason;
	}

}
