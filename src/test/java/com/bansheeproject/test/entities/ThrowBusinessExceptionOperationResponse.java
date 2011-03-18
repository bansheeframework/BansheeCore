package com.bansheeproject.test.entities;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(namespace="http://services.test.bansheeproject.com/")
public class ThrowBusinessExceptionOperationResponse {

	private String _return;
	
	public String getReturn() {
		return _return;
	}
	
	public void setReturn(String return1) {
		_return = return1;
	}
	
	
}
