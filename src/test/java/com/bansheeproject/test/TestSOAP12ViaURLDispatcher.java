package com.bansheeproject.test;

import java.io.File;

import com.bansheeproject.AddressServiceData;
import com.bansheeproject.SOAP12InvocationData;
import com.bansheeproject.WSDLServiceData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.ServiceData;
import com.bansheeproject.exceptions.WSDLRetrievalException;

public class TestSOAP12ViaURLDispatcher extends BaseSOAPTest {

	@Override
	protected WebServicesInvocationData getInvocationData()
			throws WSDLRetrievalException {

		
		ServiceData serviceData = new AddressServiceData("http://localhost:9080/soap12");
		WebServicesInvocationData invocationData = new SOAP12InvocationData(serviceData);
		return invocationData;
	}

}
