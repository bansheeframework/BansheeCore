package com.bansheeproject.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.bansheeproject.Response;
import com.bansheeproject.SOAP11InvocationData;
import com.bansheeproject.WSDLServiceData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.engine.ExceptionParameter;
import com.bansheeproject.engine.ObjectParameter;
import com.bansheeproject.engine.converters.JAXBConverter;
import com.bansheeproject.engine.converters.ObjectConverter;
import com.bansheeproject.engine.converters.ObjectConverterFactory;
import com.bansheeproject.exceptions.ServiceInvokeException;
import com.bansheeproject.exceptions.WSDLRetrievalException;
import com.bansheeproject.test.entities.BusinessExceptionData;
import com.bansheeproject.test.entities.ComplexOperation;
import com.bansheeproject.test.entities.ComplexOperationResponse;
import com.bansheeproject.test.entities.ComplexType;
import com.bansheeproject.test.entities.ThrowBusinessExceptionOperation;
import com.bansheeproject.test.entities.ThrowBusinessExceptionOperationResponse;
import com.bansheeproject.test.entities.ThrowRandomExceptionResponse;

public class TestConverters {

	
	@Test
	public void invokeLocalConverter() throws WSDLRetrievalException, ServiceInvokeException {
		WebServicesInvocationData invocationData = getInvocationData();
		
		List<Object> encoded = new ArrayList<Object>();
		List<Object> decoded = new ArrayList<Object>();
		
		TestConverter testConverter = new TestConverter(encoded, decoded);
		
		ComplexOperation complexOperation = new ComplexOperation();
		ComplexType request = new ComplexType();
		request.setArray(new Integer[]{1,2,3});
		request.setString("testString1");		
		complexOperation.setArg0(request);
		
		invocationData.setObjectParameter(complexOperation, ComplexOperationResponse.class, testConverter);
		Response response1 = invocationData.invoke();
		
		
		Assert.assertNotNull(response1);
		Assert.assertEquals(encoded.size(), 1);
		Assert.assertEquals(encoded.get(0).getClass(), ComplexOperation.class);
		Assert.assertEquals(decoded.size(), 1);
		Assert.assertEquals(decoded.get(0).getClass(), ComplexOperationResponse.class);
	}
	
	
	@Test
	public void invokeLocalConverterWithException() throws WSDLRetrievalException, ServiceInvokeException {
		
		WebServicesInvocationData invocationData = getInvocationData();
		
		List<Object> encoded = new ArrayList<Object>();
		List<Object> decoded = new ArrayList<Object>();
		
		TestConverter testConverter = new TestConverter(encoded, decoded);
		
		invocationData.setObjectParameter(new ThrowBusinessExceptionOperation(), ThrowRandomExceptionResponse.class, testConverter);
		invocationData.addExceptionType(BusinessExceptionData.class);
		Response response1 = invocationData.invoke();
		
		Assert.assertNotNull(response1);
		Assert.assertEquals(1, encoded.size());
		Assert.assertEquals(ThrowBusinessExceptionOperation.class, encoded.get(0).getClass());
		Assert.assertEquals(0, decoded.size());		
	}
	
	@Test
	public void invokeLocalConverterWithExceptionSeparateConverter() throws WSDLRetrievalException, ServiceInvokeException {
		
		WebServicesInvocationData invocationData = getInvocationData();
		
		List<Object> encoded = new ArrayList<Object>();
		List<Object> decoded = new ArrayList<Object>();
		
		TestConverter testConverter = new TestConverter(encoded, new ArrayList<Object>());
		TestConverter separateConverter = new TestConverter(new ArrayList<Object>(), decoded);
		
		invocationData.setObjectParameter(new ThrowBusinessExceptionOperation(), ThrowRandomExceptionResponse.class, testConverter);
		invocationData.addExceptionType(BusinessExceptionData.class, separateConverter);
		Response response1 = invocationData.invoke();
		
		Assert.assertNotNull(response1);
		Assert.assertEquals(1, encoded.size());
		Assert.assertEquals(ThrowBusinessExceptionOperation.class, encoded.get(0).getClass());
		Assert.assertEquals(1, decoded.size());
		Assert.assertEquals(BusinessExceptionData.class, decoded.get(0).getClass());
	}
	
	
	
	@Test
	public void invokeSharedConverter() throws WSDLRetrievalException, ServiceInvokeException {
		
		WebServicesInvocationData invocationData = getInvocationData();
		
		List<Object> encoded = new ArrayList<Object>();
		List<Object> decoded = new ArrayList<Object>();
		
		TestConverter testConverter = new TestConverter(encoded, decoded);
		invocationData.setObjectConverter(testConverter);
		
		ComplexOperation complexOperation = new ComplexOperation();
		ComplexType request = new ComplexType();
		request.setArray(new Integer[]{1,2,3});
		request.setString("testString1");		
		complexOperation.setArg0(request);
		
		invocationData.setObjectParameter(complexOperation, ComplexOperationResponse.class);
		
		Response response1 = invocationData.invoke();
		
		
		Assert.assertNotNull(response1);
		Assert.assertEquals(1, encoded.size());
		Assert.assertEquals(ComplexOperation.class, encoded.get(0).getClass());
		Assert.assertEquals(1, decoded.size());
		Assert.assertEquals(ComplexOperationResponse.class, decoded.get(0).getClass());
		
	}
	
	@Test
	public void invokeSharedConverterWithException() throws WSDLRetrievalException, ServiceInvokeException {
		
		WebServicesInvocationData invocationData = getInvocationData();
		
		List<Object> encoded = new ArrayList<Object>();
		List<Object> decoded = new ArrayList<Object>();
		
		TestConverter testConverter = new TestConverter(encoded, decoded);
		invocationData.setObjectConverter(testConverter);
		
		invocationData.addExceptionType(BusinessExceptionData.class);		
		invocationData.setObjectParameter(new ThrowBusinessExceptionOperation(), ThrowBusinessExceptionOperationResponse.class);
		
		Response response1 = invocationData.invoke();
		
		
		Assert.assertNotNull(response1);
		Assert.assertEquals(encoded.size(), 1);
		Assert.assertEquals(encoded.get(0).getClass(), ThrowBusinessExceptionOperation.class);
		Assert.assertEquals(decoded.size(), 1);
		Assert.assertEquals(decoded.get(0).getClass(), BusinessExceptionData.class);
		
	}
	
	
	public static List<Object> globalEncoded = new ArrayList<Object>();
	public static List<Object> globalDecoded = new ArrayList<Object>();
	
	
	@Test
	public void invokeGlobalConverter() throws WSDLRetrievalException, ServiceInvokeException {
		try {
			System.setProperty(ObjectConverterFactory.SYSTEM_PROPERTY, SystemObjectConverterFactory.class.getName());

			WebServicesInvocationData invocationData = getInvocationData();




			ComplexOperation complexOperation = new ComplexOperation();
			ComplexType request = new ComplexType();
			request.setArray(new Integer[]{1,2,3});
			request.setString("testString1");		
			complexOperation.setArg0(request);

			//invocationData.setServiceParameter(new ObjectParameter(complexOperation, ComplexOperationResponse.class, testConverter));
			invocationData.setObjectParameter(complexOperation, ComplexOperationResponse.class);
			Response response1 = invocationData.invoke();


			Assert.assertNotNull(response1);
			Assert.assertEquals(globalEncoded.size(), 1);
			Assert.assertEquals(globalEncoded.get(0).getClass(), ComplexOperation.class);
			Assert.assertEquals(globalDecoded.size(), 1);
			Assert.assertEquals(globalDecoded.get(0).getClass(), ComplexOperationResponse.class);
		}

		finally {
			removeGlobalConverter();
		}
	}
	
	
	@Test
	public void invokeGlobalConverterWithException() throws WSDLRetrievalException, ServiceInvokeException {
		try {
			System.setProperty(ObjectConverterFactory.SYSTEM_PROPERTY, SystemObjectConverterFactory.class.getName());

			WebServicesInvocationData invocationData = getInvocationData();

			invocationData.addExceptionType(BusinessExceptionData.class);		
			invocationData.setObjectParameter(new ThrowBusinessExceptionOperation(), ThrowBusinessExceptionOperationResponse.class);

			Response response1 = invocationData.invoke();


			Assert.assertNotNull(response1);
			Assert.assertEquals(globalEncoded.size(), 1);
			Assert.assertEquals(globalEncoded.get(0).getClass(), ThrowBusinessExceptionOperation.class);
			Assert.assertEquals(globalDecoded.size(), 1);
			Assert.assertEquals(globalDecoded.get(0).getClass(), BusinessExceptionData.class);
		}
		finally {
			removeGlobalConverter();
		}
	}
	
	
	
	public void removeGlobalConverter() {
		System.setProperty(ObjectConverterFactory.SYSTEM_PROPERTY, ObjectConverterFactory.DEFAULT_FACTORY);
		globalDecoded.clear();
		globalEncoded.clear();
	}
	
	
	
	
	private WebServicesInvocationData getInvocationData() throws WSDLRetrievalException {
		WSDLServiceData serviceData = WSDLServiceData.loadFromFile(new File("src\\test\\resources\\wsdl\\soap11.wsdl"));
		WebServicesInvocationData invocationData = new SOAP11InvocationData(serviceData);
		return invocationData;
	}
	
	
	
	public class TestConverter extends JAXBConverter {

		List<Object> encoded ;
		
		List<Object> decoded;
		
		public TestConverter(List<Object> encoded, List<Object> decoded) {
			this.encoded = encoded;
			this.decoded = decoded;
		}
		
		@Override
		public String encode(Object source) {
			encoded.add(source);
			return super.encode(source);
		}
		
		@Override
		public Object decode(String response, Class<?> expectedType) {
			Object responseObject = super.decode(response, expectedType);
			decoded.add(responseObject);
			return responseObject;
		}
		
		
	}
	
	public static class SystemObjectConverterFactory extends ObjectConverterFactory {

		@Override
		public ObjectConverter newObjectConverter() {			
			return new SystemConverter();
		}
		
	}
	
	public static class SystemConverter extends JAXBConverter {
		
		@Override
		public String encode(Object source) {
			globalEncoded.add(source);
			return super.encode(source);
		}
		
		@Override
		public Object decode(String response, Class<?> expectedType) {
			Object responseObject = super.decode(response, expectedType);
			globalDecoded.add(responseObject);
			return responseObject;
		}
	}
	
	
	
	
}
