package com.bansheeproject.test.entities;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(namespace="http://services.test.bansheeproject.com/")
public class ComplexOperation {

	

	private ComplexType arg0;

	public ComplexType getArg0() {
		return arg0;
	}

	public void setArg0(ComplexType arg0) {
		this.arg0 = arg0;
	}
	
	
}
