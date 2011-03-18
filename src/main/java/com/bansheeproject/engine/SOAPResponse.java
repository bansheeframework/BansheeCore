package com.bansheeproject.engine;

import javax.xml.soap.SOAPMessage;

import com.bansheeproject.Response;

public class SOAPResponse extends Response {

	
	private ServiceParameter responseData;
	
	public SOAPResponse(ServiceParameter responseData) {
		this.responseData = responseData;
	}
	
	
	@Override
	public Object getResponseData() {		
		return responseData.decode();
	}

}
