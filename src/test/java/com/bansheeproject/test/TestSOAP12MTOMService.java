package com.bansheeproject.test;

import java.io.File;

import org.testng.annotations.Test;

import com.bansheeproject.SOAP12InvocationData;
import com.bansheeproject.WSDLServiceData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.ServiceData;
import com.bansheeproject.exceptions.WSDLRetrievalException;


@Test
public class TestSOAP12MTOMService extends BaseSOAPTest{




	@Override
	protected WebServicesInvocationData getInvocationData()
			throws WSDLRetrievalException {
		File f = new File("src\\test\\resources\\wsdl\\soap12MTOM.wsdl");
		
		ServiceData serviceData = WSDLServiceData.loadFromFile(f);
		WebServicesInvocationData invocationData = new SOAP12InvocationData(serviceData);
		return invocationData;
	}
	
	
	
	
	
}
