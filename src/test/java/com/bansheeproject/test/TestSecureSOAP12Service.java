package com.bansheeproject.test;

import java.io.File;
import java.io.InputStream;

import org.testng.annotations.Test;

import com.bansheeproject.SOAP11InvocationData;
import com.bansheeproject.SOAP12InvocationData;
import com.bansheeproject.SOAPInvocationData;
import com.bansheeproject.WSDLServiceData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.ServiceData;
import com.bansheeproject.exceptions.WSDLRetrievalException;
import com.bansheeproject.features.SSLAliasSelectorFeature;


@Test
public class TestSecureSOAP12Service extends BaseSOAPTest{

	
	
	protected WebServicesInvocationData getInvocationData() throws WSDLRetrievalException {
		File f = new File("src\\test\\resources\\wsdl\\secureSOAP12.wsdl");
		ServiceData serviceData = WSDLServiceData.loadFromFile(f);
		SOAPInvocationData invocationData = new SOAP12InvocationData(serviceData);
		InputStream keyStore = TestSecureSOAP12Service.class.getResourceAsStream("/keystore.jks");	
		InputStream trustStore = TestSecureSOAP12Service.class.getResourceAsStream("/keystore.jks");
		invocationData.addSecurityLayer("secureSOAP12", keyStore, "test", trustStore, "test");
		return invocationData;
	}
	
	
	
	
}
