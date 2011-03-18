package com.bansheeproject.test;

import java.io.File;
import java.io.InputStream;

import org.testng.annotations.Test;

import com.bansheeproject.SOAP11InvocationData;
import com.bansheeproject.SOAPInvocationData;
import com.bansheeproject.WSDLServiceData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.ServiceData;
import com.bansheeproject.exceptions.WSDLRetrievalException;



public class TestSecureSOAP11Service {

	
	protected WebServicesInvocationData getInvocationData() throws WSDLRetrievalException {
		
		
		File f = new File("src\\test\\resources\\wsdl\\secureSOAP11.wsdl");
		ServiceData serviceData = WSDLServiceData.loadFromFile(f); 
		
		SOAPInvocationData invocationData = new SOAP11InvocationData(serviceData);
		InputStream keyStore = TestSecureSOAP11Service.class.getResourceAsStream("/keystore.jks");		
		invocationData.addSecurityLayer("secureSOAP11", keyStore, "test", keyStore, "test");
		return invocationData;
	}
	
	
	
}
