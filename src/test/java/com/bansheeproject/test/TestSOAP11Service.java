package com.bansheeproject.test;

import java.io.File;

import org.testng.annotations.Test;

import com.bansheeproject.SOAP11InvocationData;
import com.bansheeproject.WSDLServiceData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.ServiceData;
import com.bansheeproject.exceptions.WSDLRetrievalException;

@Test
public class TestSOAP11Service extends BaseSOAPTest{


	@Override
	protected WebServicesInvocationData getInvocationData()
			throws WSDLRetrievalException {
		
		File f = new File("src\\test\\resources\\wsdl\\soap11.wsdl");
		
		ServiceData serviceData = WSDLServiceData.loadFromFile(f);
		WebServicesInvocationData webServicesInvocationData = new SOAP11InvocationData(serviceData);
		return webServicesInvocationData;
	}
	
	
	
}
