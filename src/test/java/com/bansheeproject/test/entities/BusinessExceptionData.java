package com.bansheeproject.test.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;



@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="BusinessException", namespace="http://services.test.bansheeproject.com/")

public class BusinessExceptionData{

	
	@XmlElement
	private String message;
	
	public BusinessExceptionData() {
		super();

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
