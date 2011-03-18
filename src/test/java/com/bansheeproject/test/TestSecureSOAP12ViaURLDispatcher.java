package com.bansheeproject.test;

import java.io.InputStream;

import com.bansheeproject.AddressServiceData;
import com.bansheeproject.SOAP12InvocationData;
import com.bansheeproject.SOAPInvocationData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.exceptions.WSDLRetrievalException;

public class TestSecureSOAP12ViaURLDispatcher extends BaseSOAPTest{

	@Override
	protected WebServicesInvocationData getInvocationData()
			throws WSDLRetrievalException {


		AddressServiceData serviceData = new AddressServiceData("https://127.0.0.1:443/secureSOAP12");		
		SOAPInvocationData invocationData = new SOAP12InvocationData(serviceData);
		InputStream keyStore = TestSecureSOAP12ViaURLDispatcher.class.getResourceAsStream("/keystore.jks");	
		InputStream trustStore = TestSecureSOAP12ViaURLDispatcher.class.getResourceAsStream("/keystore.jks");
		invocationData.addSecurityLayer("secureSOAP12ViaURL", keyStore, "test", trustStore, "test");
		return invocationData;
		
		
	}

}
