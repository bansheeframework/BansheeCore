package com.bansheeproject.engine;


/**
 * Represents parameters/responses to and from services. It is
 * also responsible for parsing responses.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public abstract class ServiceParameter {

	public abstract String encode();
	
	public abstract Object decode();
	
	public abstract ServiceParameter resolveResponse (String response); 
	
	
}
