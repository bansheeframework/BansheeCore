package com.bansheeproject.test;

import java.io.File;
import java.io.InputStream;

import org.testng.annotations.Test;

import com.bansheeproject.SOAP12InvocationData;
import com.bansheeproject.SOAPInvocationData;
import com.bansheeproject.WSDLServiceData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.ServiceData;
import com.bansheeproject.exceptions.WSDLRetrievalException;


@Test
public class TestSecureSOAP12MTOMService extends BaseSOAPTest{

	

	
	
	protected WebServicesInvocationData getInvocationData() throws WSDLRetrievalException {
		
		
		File f = new File("src\\test\\resources\\wsdl\\secureSOAP12MTOM.wsdl");
		ServiceData serviceData = WSDLServiceData.loadFromFile(f);
		
		
		SOAPInvocationData invocationData = new SOAP12InvocationData(serviceData);
		InputStream keyStore = TestSecureSOAP12MTOMService.class.getResourceAsStream("/keystore.jks");
		InputStream trustStore = TestSecureSOAP12MTOMService.class.getResourceAsStream("/keystore.jks");
		invocationData.addSecurityLayer("secureSOAP12MTOM", keyStore, "test", trustStore, "test");
		return invocationData;
	}
	
	

	
	
}
