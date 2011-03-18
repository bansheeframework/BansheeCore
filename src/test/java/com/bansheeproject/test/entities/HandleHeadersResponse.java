package com.bansheeproject.test.entities;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(namespace="http://services.test.bansheeproject.com/")
public class HandleHeadersResponse {

	private String _return;
	
	
	public String getReturn() {
		return _return;
	}
	
	public void setReturn(String _return) {
		this._return = _return;
	}
	
}
