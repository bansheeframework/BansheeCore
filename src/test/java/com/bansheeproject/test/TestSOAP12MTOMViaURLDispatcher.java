package com.bansheeproject.test;

import com.bansheeproject.AddressServiceData;
import com.bansheeproject.SOAP12InvocationData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.ServiceData;
import com.bansheeproject.exceptions.WSDLRetrievalException;

public class TestSOAP12MTOMViaURLDispatcher extends BaseSOAPTest{
	
	
	@Override
	protected WebServicesInvocationData getInvocationData()
			throws WSDLRetrievalException {
		
		ServiceData serviceData = new AddressServiceData("http://localhost:9080/soap12MTOM");
		WebServicesInvocationData invocationData = new SOAP12InvocationData(serviceData);
		return invocationData;
	}

}
