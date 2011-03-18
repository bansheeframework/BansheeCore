package com.bansheeproject.test;

import com.bansheeproject.AddressServiceData;
import com.bansheeproject.SOAP11InvocationData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.ServiceData;
import com.bansheeproject.exceptions.WSDLRetrievalException;

public class TestSOAP11MTOMViaURLDispatcher extends BaseSOAPTest{

	@Override
	protected WebServicesInvocationData getInvocationData()
			throws WSDLRetrievalException {
		
		ServiceData serviceData = new AddressServiceData("http://localhost:9080/soap11MTOM");
		WebServicesInvocationData webServicesInvocationData = new SOAP11InvocationData(serviceData);
		return webServicesInvocationData;
	}

}
