package com.bansheeproject.test;

import java.io.File;
import java.io.InputStream;

import com.bansheeproject.AddressServiceData;
import com.bansheeproject.SOAP11InvocationData;
import com.bansheeproject.SOAPInvocationData;
import com.bansheeproject.WSDLServiceData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.ServiceData;
import com.bansheeproject.exceptions.WSDLRetrievalException;

public class TestSecureSOAP11ViaURLDispatcher extends BaseSOAPTest{

	@Override
	protected WebServicesInvocationData getInvocationData()
			throws WSDLRetrievalException {
		
		
		AddressServiceData serviceData = new AddressServiceData("https://127.0.0.1:443/secureSOAP11");		
		SOAPInvocationData invocationData = new SOAP11InvocationData(serviceData);
		InputStream keyStore = TestSecureSOAP11Service.class.getResourceAsStream("/keystore.jks");	
		InputStream trustStore = TestSecureSOAP11Service.class.getResourceAsStream("/keystore.jks");
		invocationData.addSecurityLayer("secureSOAP11ViaURL", keyStore, "test", trustStore, "test");
		return invocationData;
		
	}

}
