package com.bansheeproject.engine.wsdl;

import javax.xml.namespace.QName;


/**
 * Contains metadata about a WSDL´s operation.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class WSDLOperation {
	
	
	private String namespace;
	
	private String name;
	
	
	public String getName() {
		return name;
	}
	
	public String getNamespace() {
		return namespace;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public QName getQName() {
		return new QName(namespace, name);
	}

}
