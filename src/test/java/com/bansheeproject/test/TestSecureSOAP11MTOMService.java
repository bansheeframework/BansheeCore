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


@Test
public class TestSecureSOAP11MTOMService extends BaseSOAPTest{

	
	
	protected WebServicesInvocationData getInvocationData() throws WSDLRetrievalException {
		
		File f = new File("src\\test\\resources\\wsdl\\secureSOAP11MTOM.wsdl");
		
		ServiceData serviceData = WSDLServiceData.loadFromFile(f);
		SOAPInvocationData invocationData = new SOAP11InvocationData(serviceData);
		InputStream keyStore = TestSecureSOAP11MTOMService.class.getResourceAsStream("/keystore.jks");		
		InputStream trustStore = TestSecureSOAP11MTOMService.class.getResourceAsStream("/keystore.jks");
		invocationData.addSecurityLayer("secureSOAP11MTOM", keyStore, "test", trustStore, "test");
		return invocationData;
	}
	
	
	
	
	
	
}
