package com.bansheeproject.engine.wsdl;


/**
 * Contains metadata about a WSDL´s port.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class WSDLPort {

	private String name;

	public String getName() {
		return name;
	}

	public WSDLPort(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String toString() {
		
		return "WSDL Port: ".concat(name);
	}
}
