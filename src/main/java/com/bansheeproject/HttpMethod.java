package com.bansheeproject;


/**
 * Represents different types of HTTP methods.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 *
 */
public enum HttpMethod {

	GET, POST, PUT, DELETE, OPTIONS, HEAD;
	
	public String getRepresentation() {
		return this.name();
	}
	
	@Override
	public String toString() {
		
		return "HttpMethod."+name();
	}
	
}
