package com.bansheeproject.test;

import java.io.File;

import com.bansheeproject.SOAP12InvocationData;
import com.bansheeproject.WSDLServiceData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.ServiceData;
import com.bansheeproject.exceptions.WSDLRetrievalException;

public class TestSOAP12Service extends BaseSOAPTest{


	@Override
	protected WebServicesInvocationData getInvocationData()
			throws WSDLRetrievalException {
		
		File f = new File("src\\test\\resources\\wsdl\\soap12.wsdl");
		
		ServiceData serviceData = WSDLServiceData.loadFromFile(f);
		WebServicesInvocationData invocationData = new SOAP12InvocationData(serviceData);
		return invocationData;
	}
	
	
	
	
	
}
