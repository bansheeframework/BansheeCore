package com.bansheeproject.test.entities;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="AnotherBusinessException", namespace="http://services.test.bansheeproject.com/")
public class AnotherBusinessExceptionBean {

	private String message;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
