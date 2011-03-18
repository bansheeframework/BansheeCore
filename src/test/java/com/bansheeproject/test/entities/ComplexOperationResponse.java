package com.bansheeproject.test.entities;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(namespace="http://services.test.bansheeproject.com/")
public class ComplexOperationResponse {

	private ComplexType _return;

	public ComplexType getReturn() {
		return _return;
	}

	public void setReturn(ComplexType _return) {
		this._return = _return;
	}
	
}
