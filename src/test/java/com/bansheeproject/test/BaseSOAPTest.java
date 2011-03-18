package com.bansheeproject.test;

import java.io.ByteArrayInputStream;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.bansheeproject.ExceptionResponse;
import com.bansheeproject.Response;
import com.bansheeproject.SOAPInvocationData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.exceptions.ServiceInvokeException;
import com.bansheeproject.exceptions.WSDLRetrievalException;
import com.bansheeproject.test.entities.AnotherBusinessExceptionBean;
import com.bansheeproject.test.entities.BusinessExceptionData;
import com.bansheeproject.test.entities.ComplexOperation;
import com.bansheeproject.test.entities.ComplexOperationResponse;
import com.bansheeproject.test.entities.ComplexType;
import com.bansheeproject.test.entities.HandleHeaders;
import com.bansheeproject.test.entities.HandleHeadersResponse;
import com.bansheeproject.test.entities.HangOperation;
import com.bansheeproject.test.entities.HangOperationResponse;
import com.bansheeproject.test.entities.HangThenThrowExceptionOperation;
import com.bansheeproject.test.entities.HangThenThrowExceptionOperationResponse;
import com.bansheeproject.test.entities.OneWayOperation;
import com.bansheeproject.test.entities.OneWayOperationResponse;
import com.bansheeproject.test.entities.StringOperation;
import com.bansheeproject.test.entities.StringOperationResponse;
import com.bansheeproject.test.entities.ThrowBusinessExceptionOperation;
import com.bansheeproject.test.entities.ThrowBusinessExceptionOperationResponse;
import com.bansheeproject.test.entities.ThrowRandomException;
import com.bansheeproject.test.entities.ThrowRandomExceptionResponse;




public abstract class BaseSOAPTest {

	

	
	
		
	@Test
	public void invokeStringOperationViaString() throws ServiceInvokeException, WSDLRetrievalException {
		WebServicesInvocationData invocationData = getInvocationData();
		invocationData.setStringParameter("<ser:stringOperation xmlns:ser=\"http://services.test.bansheeproject.com/\"><arg0>test</arg0></ser:stringOperation>");
		
		
		Response response = invocationData.invoke();
		
		Assert.assertFalse(response.isExceptionResponse());
		Assert.assertEquals(String.class, response.getResponseType());
		Assert.assertTrue(response.getResponseData().toString().contains("stringOperationResponse"));
		Assert.assertTrue(response.getResponseData().toString().contains("test"));
			
		
	}
	
	
	@Test
	public void invokeStringOperationViaObject() throws ServiceInvokeException, WSDLRetrievalException {
		WebServicesInvocationData invocationData = getInvocationData();
		StringOperation param = new StringOperation();
		param.setArg0("teste");
		
		invocationData.setObjectParameter(param, StringOperationResponse.class);
		
		Response response = invocationData.invoke();
		
		
		Assert.assertFalse(response.isExceptionResponse());
		Assert.assertEquals(response.getResponseData().getClass(), StringOperationResponse.class);
		Assert.assertEquals("teste", ((StringOperationResponse)response.getResponseData()).getReturn());
		
		
	}
	
	
	@Test
	public void invokeOneWayOperationViaString() throws ServiceInvokeException, WSDLRetrievalException {
		WebServicesInvocationData invocationData = getInvocationData();
		invocationData.setStringParameter("<ser:oneWayOperation xmlns:ser=\"http://services.test.bansheeproject.com/\"><arg0>test</arg0></ser:oneWayOperation>");
		
		Response oneWayResponse = invocationData.invokeOneWay();
		
		
		Assert.assertNull(oneWayResponse);
		
	}
	
	
	@Test
	public void invokeOneWayOperationViaTwoWayString() throws ServiceInvokeException, WSDLRetrievalException {
		WebServicesInvocationData invocationData = getInvocationData();
		invocationData.setStringParameter("<ser:oneWayOperation xmlns:ser=\"http://services.test.bansheeproject.com/\"><arg0>test</arg0></ser:oneWayOperation>");
		
		Response twoWayResponse = invocationData.invoke();
		
		
		Assert.assertNotNull(twoWayResponse);
		Assert.assertFalse(twoWayResponse.isExceptionResponse());
		Assert.assertTrue(twoWayResponse.getResponseData().toString().contains("oneWayOperationResponse"));		
	}
	
	
	@Test
	public void invokeOneWayOperationViaObject() throws ServiceInvokeException, WSDLRetrievalException {
		WebServicesInvocationData invocationData = getInvocationData();
		
		OneWayOperation oneWayOperation = new OneWayOperation();
		oneWayOperation.setArg0("test");
		
		invocationData.setObjectParameter(oneWayOperation, OneWayOperationResponse.class);
		Response response = invocationData.invokeOneWay();
		
		Assert.assertNull(response);
		
		
	}
	
	
	@Test
	public void invokeOneWayOperationViaTwoWayObject() throws ServiceInvokeException, WSDLRetrievalException {
		WebServicesInvocationData invocationData = getInvocationData();
		
		OneWayOperation oneWayOperation = new OneWayOperation();
		oneWayOperation.setArg0("test");
		
		invocationData.setObjectParameter(oneWayOperation, OneWayOperationResponse.class);
		Response response = invocationData.invoke();
		
		Assert.assertNotNull(response);
		Assert.assertFalse(response.isExceptionResponse());
		Assert.assertEquals(OneWayOperationResponse.class, response.getResponseData().getClass());
		
		
	}
	
	
	@Test
	public void invokeComplexOperationViaObject() throws ServiceInvokeException, WSDLRetrievalException {
		WebServicesInvocationData invocationData = getInvocationData();
		
		ComplexOperation complexOperation = new ComplexOperation();
		ComplexType request = new ComplexType();
		request.setArray(new Integer[]{1,2,3});
		request.setString("testString");
		complexOperation.setArg0(request);
		invocationData.setObjectParameter(complexOperation, ComplexOperationResponse.class);
		
		
		Response response = invocationData.invoke();
		
		Assert.assertNotNull(response);
		Assert.assertFalse(response.isExceptionResponse());
		Assert.assertEquals(response.getResponseData().getClass(), ComplexOperationResponse.class);
		
		ComplexOperationResponse responseData = (ComplexOperationResponse)response.getResponseData();
		ComplexType responseComplexType =  responseData.getReturn();
		
		Assert.assertNotNull(responseComplexType);
		Assert.assertEquals(responseComplexType.getArray(), new Integer[]{1,2,3});
		Assert.assertEquals(responseComplexType.getString(), "testString");
		
	}
	
	@Test
	public void invokeComplexOperationViaString() throws ServiceInvokeException, WSDLRetrievalException, XPathExpressionException {
		
		WebServicesInvocationData invocationData = getInvocationData();
		String requestData = "<ns1:complexOperation xmlns:ns1=\"http://services.test.bansheeproject.com/\"><arg0><array>1</array><array>2</array><array>3</array><string>testString</string></arg0></ns1:complexOperation>";
		invocationData.setStringParameter(requestData);
		Response response = invocationData.invoke();
		
		
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getResponseData().getClass(), String.class);
		
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		InputSource source = new InputSource(new ByteArrayInputStream(response.getResponseData().toString().getBytes()));
		
		
		
		
		NodeList nodeList = (NodeList)xpath.evaluate("//*[local-name() = 'array']", source, XPathConstants.NODESET);
		
		Assert.assertEquals(nodeList.getLength(), 3);
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			Assert.assertEquals(Integer.valueOf(node.getTextContent()), new Integer( i + 1));
		}
		
		source = new InputSource(new ByteArrayInputStream(response.getResponseData().toString().getBytes()));
		
		String stringSent = (String)xpath.evaluate("//*[local-name() = 'string']/.", source, XPathConstants.STRING);
		
		Assert.assertEquals(stringSent, "testString");
		
		
	}
	
	@Test
	public void invokeComplexOperationWithGarbageDataViaString() throws WSDLRetrievalException, ServiceInvokeException, XPathExpressionException {
		WebServicesInvocationData invocationData = getInvocationData();
		String requestData = "<ns1:complexOperation xmlns:ns1=\"http://services.test.bansheeproject.com/\"><arg0><garbage>test</garbage> <array>1</array><array>2</array><array>3</array><string>testString</string></arg0></ns1:complexOperation>";
		invocationData.setStringParameter(requestData);
		Response response = invocationData.invoke();
		
		
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getResponseData().getClass(), String.class);
		
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		InputSource source = new InputSource(new ByteArrayInputStream(response.getResponseData().toString().getBytes()));
		
		
		
		
		NodeList nodeList = (NodeList)xpath.evaluate("//*[local-name() = 'array']", source, XPathConstants.NODESET);
		
		Assert.assertEquals(nodeList.getLength(), 3);
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			Assert.assertEquals(Integer.valueOf(node.getTextContent()), new Integer( i + 1));
		}
		
		source = new InputSource(new ByteArrayInputStream(response.getResponseData().toString().getBytes()));
		
		String stringSent = (String)xpath.evaluate("//*[local-name() = 'string']/.", source, XPathConstants.STRING);
		
		Assert.assertEquals(stringSent, "testString");
	}
	
	@Test
	public void invokeComplexOperationWithGarbageData2ViaString() throws WSDLRetrievalException, ServiceInvokeException, XPathExpressionException {
		WebServicesInvocationData invocationData = getInvocationData();
		String requestData = "<ns1:complexOperation xmlns:ns1=\"http://services.test.bansheeproject.com/\"><garbage xmlns=\"nothing\">anything</garbage><arg0><garbage>test</garbage> <array>1</array><array>2</array><array>3</array><string>testString</string></arg0></ns1:complexOperation>";
		invocationData.setStringParameter(requestData);
		Response response = invocationData.invoke();
		
		
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getResponseData().getClass(), String.class);
		
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		InputSource source = new InputSource(new ByteArrayInputStream(response.getResponseData().toString().getBytes()));
		
		
		
		
		NodeList nodeList = (NodeList)xpath.evaluate("//*[local-name() = 'array']", source, XPathConstants.NODESET);
		
		Assert.assertEquals(nodeList.getLength(), 3);
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			Assert.assertEquals(Integer.valueOf(node.getTextContent()), new Integer( i + 1));
		}
		
		source = new InputSource(new ByteArrayInputStream(response.getResponseData().toString().getBytes()));
		
		String stringSent = (String)xpath.evaluate("//*[local-name() = 'string']/.", source, XPathConstants.STRING);
		
		Assert.assertEquals(stringSent, "testString");
	}
	
	@Test
	public void invokeException() throws ServiceInvokeException, WSDLRetrievalException {
		WebServicesInvocationData invocationData = getInvocationData();
		
		invocationData.addExceptionType(BusinessExceptionData.class);
		
		ThrowBusinessExceptionOperation operation = new ThrowBusinessExceptionOperation();
		invocationData.setObjectParameter(operation, ThrowBusinessExceptionOperationResponse.class);
		
		
		Response response = invocationData.invoke();
		
		Assert.assertTrue(response.isExceptionResponse());
		Assert.assertEquals(response.getClass(), ExceptionResponse.class);
		
		ExceptionResponse exceptionResponse = (ExceptionResponse)response;
		
		Object responseData = exceptionResponse.getResponseData();
		
		Assert.assertEquals(responseData.getClass(), BusinessExceptionData.class);
		BusinessExceptionData businessExceptionData = (BusinessExceptionData)responseData;
		
		Assert.assertEquals(businessExceptionData.getMessage(), "should throw an exception");
		
	}
	
	
	@Test
	public void invokeExceptionAsString() throws ServiceInvokeException, Exception {
		WebServicesInvocationData invocationData = getInvocationData();
		
		
		invocationData.setHandleExceptionAsString(true);
		
		ThrowBusinessExceptionOperation operation = new ThrowBusinessExceptionOperation();
		invocationData.setObjectParameter(operation, ThrowBusinessExceptionOperationResponse.class);
		
		
		Response response = invocationData.invoke();
		
		
		Assert.assertTrue(response.isExceptionResponse());
		Assert.assertEquals(response.getClass(), ExceptionResponse.class);
		
		ExceptionResponse exceptionResponse = (ExceptionResponse)response;
		
		Object responseData = exceptionResponse.getResponseData();
		
		Assert.assertEquals(responseData.getClass(), String.class);
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		InputSource source = new InputSource(new ByteArrayInputStream(((String)responseData).getBytes()));
		String exceptionString = (String)xpath.evaluate("//*[local-name() = 'message']/.", source, XPathConstants.STRING);
		
		Assert.assertEquals(exceptionString, "should throw an exception");
		
		
		
	}
	
	

	
	@Test()
	public void invokeExceptionAsStringDoesntHandle() throws ServiceInvokeException, Exception {
		WebServicesInvocationData invocationData = getInvocationData();
		
		
		invocationData.setHandleExceptionAsString(false);
		
		ThrowBusinessExceptionOperation operation = new ThrowBusinessExceptionOperation();
		invocationData.setObjectParameter(operation, ThrowBusinessExceptionOperationResponse.class);
		
		
		
		
		String message = null;
		
		
		try {
			invocationData.invoke();
		}
		catch (ServiceInvokeException ex) {
			message = ex.getMessage();
		}
		
		Assert.assertNotNull(message);
		Assert.assertTrue(message.contains("should throw an exception"));
		
		
		
	}
	
	
	
	@Test(expectedExceptions={ServiceInvokeException.class})
	public void invokeOperationInvalidString() throws WSDLRetrievalException, ServiceInvokeException {
		WebServicesInvocationData invocationData = getInvocationData();
		
		String invalidString = "asdkhsds";
		
		invocationData.setStringParameter(invalidString);
		
		invocationData.invoke();
		
	}
	
	
	@Test(expectedExceptions={IllegalStateException.class})
	public void invokeOperationNullParameter() throws WSDLRetrievalException, ServiceInvokeException {
		WebServicesInvocationData invocationData = getInvocationData();
		
	
		invocationData.invoke();
		
	}
	
	
	@Test(expectedExceptions={ServiceInvokeException.class})
	public void invokeHangOperation() throws WSDLRetrievalException, ServiceInvokeException {
		
		WebServicesInvocationData invocationData = getInvocationData();
		
		
		invocationData.setObjectParameter(new HangOperation(), HangOperationResponse.class);
		invocationData.setTimeout(1000);
		invocationData.invoke();
		
	}
	
	
	@Test
	public void invokeHangAndThrowExceptionOperation() throws WSDLRetrievalException, ServiceInvokeException {
		
		WebServicesInvocationData invocationData = getInvocationData();
		
		invocationData.setObjectParameter(new HangThenThrowExceptionOperation(), HangThenThrowExceptionOperationResponse.class);
		invocationData.addExceptionType(BusinessExceptionData.class);
		invocationData.setTimeout(5000);
		Response response = invocationData.invoke();
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.isExceptionResponse());
		Object responseData = response.getResponseData();
		
		Assert.assertNotNull(responseData);
		Assert.assertEquals(responseData.getClass(), BusinessExceptionData.class);
		
		
		
	}
	
	@Test
	public void invokeWithHeaders () throws WSDLRetrievalException, ServiceInvokeException {
		WebServicesInvocationData invocationData = getInvocationData();
		SOAPInvocationData soapData = (SOAPInvocationData)invocationData;
		
		soapData.addHeaderString("<ser:arg0 xmlns:ser=\"http://services.test.bansheeproject.com/\">arg</ser:arg0>");
		soapData.setObjectParameter(new HandleHeaders(), HandleHeadersResponse.class);
		
		Response response = soapData.invoke();
		
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getResponseType(), HandleHeadersResponse.class);
		HandleHeadersResponse responseData = (HandleHeadersResponse)response.getResponseData();
		
		Assert.assertNotNull(responseData);
		Assert.assertEquals(responseData.getReturn(), "arg");
		
	}
	
	@Test
	public void invokeThrowRandomException() throws WSDLRetrievalException, ServiceInvokeException {
		WebServicesInvocationData invocationData = getInvocationData();
		
		invocationData.setObjectParameter(new ThrowRandomException(), ThrowRandomExceptionResponse.class);
		invocationData.addExceptionType(BusinessExceptionData.class);
		invocationData.addExceptionType(AnotherBusinessExceptionBean.class);
		
		for (int i = 0; i < 10; i++) {
			Response response = invocationData.invoke();

			Assert.assertNotNull(response);
			Assert.assertTrue(response.isExceptionResponse());
			boolean isExpectedExceptionType = response.getResponseType().equals(BusinessExceptionData.class);
			isExpectedExceptionType |= response.getResponseType().equals(AnotherBusinessExceptionBean.class);
			
			Assert.assertTrue(isExpectedExceptionType);
		}
		
		
	}
	
	
	
	@Test()
	public void invokeLoggedOperation() throws WSDLRetrievalException, ServiceInvokeException {
		SOAPInvocationData invocationData = (SOAPInvocationData)getInvocationData();
		TestLogger logger = new TestLogger();
		
		invocationData.logOperations(logger);
		
		String parameter = "<ser:stringOperation xmlns:ser=\"http://services.test.bansheeproject.com/\"><arg0>test</arg0></ser:stringOperation>";
		
		invocationData.setStringParameter(parameter);
		
		
		Response response = invocationData.invoke();
		
		Assert.assertTrue(logger.infoMessages.get(0).contains(parameter));
		Assert.assertTrue(logger.infoMessages.get(1).contains(response.getResponseData().toString()));
		
	}
	
	
	@Test(expectedExceptions={ServiceInvokeException.class})
	public void invokeMisleadAddress() throws WSDLRetrievalException, ServiceInvokeException {
		SOAPInvocationData invocationData = (SOAPInvocationData)getInvocationData();
		
		invocationData.overwriteInvocationAddress("http://nonexistentaddress/");
		
		String parameter = "<ser:stringOperation xmlns:ser=\"http://services.test.bansheeproject.com/\"><arg0>test</arg0></ser:stringOperation>";
		
		invocationData.setStringParameter(parameter);
		
		
		invocationData.invoke();
	}
	
	protected abstract WebServicesInvocationData getInvocationData() throws WSDLRetrievalException;
	
}
