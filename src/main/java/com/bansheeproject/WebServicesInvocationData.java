package com.bansheeproject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bansheeproject.engine.BansheeDispatcher;
import com.bansheeproject.engine.ExceptionParameter;
import com.bansheeproject.engine.InvocationContext;
import com.bansheeproject.engine.ObjectParameter;
import com.bansheeproject.engine.ServiceParameter;
import com.bansheeproject.engine.StringParameter;
import com.bansheeproject.engine.converters.ObjectConverter;
import com.bansheeproject.engine.converters.ObjectConverterFactory;
import com.bansheeproject.exceptions.ServiceInvokeException;
import com.bansheeproject.features.Feature;
import com.bansheeproject.features.ServiceTimeout;
import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;

/**
 * It is an abstraction of a service invocation.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public abstract class WebServicesInvocationData {
	
	
	private ServiceParameter serviceParameter;
	
	private ServiceData serviceData;
	
	private Set<Feature> features;
	
	private List<ExceptionParameter> exceptionTypes = new ArrayList<ExceptionParameter>();	
	
	private ObjectConverter objectConverter;

	private boolean handleExceptionAsString;
	
	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(WebServicesInvocationData.class);
	
	public WebServicesInvocationData(ServiceData serviceData) {
		if (serviceData == null) {
			logger.warn("Trying to instantiate a WebServicesInvocationData with null service data. Throwing exception..");
			throw new IllegalArgumentException("Service data cannot be null");
		}
		this.serviceData = serviceData;
		this.features = new HashSet<Feature>();
		this.objectConverter = ObjectConverterFactory.newInstance().newObjectConverter();
		
	}

	protected ServiceParameter getServiceParameter() {
		return serviceParameter;
	}

	protected void setServiceParameter(ServiceParameter serviceParameter) {
		if (serviceParameter == null)
			throw new IllegalArgumentException("Service Parameter cannot be null.");
		this.serviceParameter = serviceParameter;
	}
	
	public void setStringParameter (String stringParameter) {
		if (stringParameter == null)
			throw new IllegalArgumentException("Parameter cannot be null.");
		setServiceParameter(new StringParameter(stringParameter));
	}
	
	public void setObjectParameter (Object objectParameter, Class expectedResponseType)
	{
		if (objectParameter == null)
			throw new IllegalArgumentException("Object parameter cannot be null.");		
		setServiceParameter(new ObjectParameter(objectParameter, expectedResponseType, getObjectConverter()));			
	}
	
	public void setObjectParameter (Object objectParameter, Class expectedResponseType, ObjectConverter objectConverter)
	{
		if (objectParameter == null)
			throw new IllegalArgumentException("Object parameter cannot be null.");
		if (objectConverter == null)
			throw new IllegalArgumentException("Object converter cannot be null.");
		setServiceParameter(new ObjectParameter(objectParameter, expectedResponseType, objectConverter));			
	}
	
	public ServiceData getServiceData() {
		return this.serviceData;
	}	
	
	protected void addFeature (Feature feature) {
		logger.debug(new StringBuilder("Inserting feature: ").append(feature));
		if (feature == null)
			throw new IllegalArgumentException("You cannot add a null feature.");
		features.add(feature);
	}
	
	public void clearFeatures () {
		features.clear();
	}
	
	public Collection<Feature> getFeatureSet() {
		Set<Feature> feature = new HashSet<Feature>(features);
		return feature;
	}

	public ObjectConverter getObjectConverter() {
		return objectConverter;
	}

	public void setObjectConverter(ObjectConverter objectConverter) {
		logger.debug(new StringBuilder("Setting object converter: ").append(objectConverter));
		if (objectConverter == null)
			throw new IllegalArgumentException("The object converter cannot be null.");
		this.objectConverter = objectConverter;
	}
	
	public void addExceptionType (Class<?> exceptionData) {
		logger.debug(new StringBuilder("Adding exception type: ").append(exceptionData));
		if (exceptionData == null) {
			logger.warn("Exception data is null. Throwing exception...");
			throw new IllegalArgumentException("The exception type cannot be null.");
		}
		exceptionTypes.add(new ExceptionParameter(exceptionData, getObjectConverter()));		
	}
	
	public void addExceptionType (Class<?> exceptionData, ObjectConverter converter) {
		logger.debug(new StringBuilder("Adding exception type: ").append(exceptionData));
		if (exceptionData == null) {
			logger.warn("Exception data is null. Throwing exception...");
			throw new IllegalArgumentException("The exception type cannot be null.");
		}
		exceptionTypes.add(new ExceptionParameter(exceptionData, converter));		
	}
	
	protected void addExceptionParameter(ExceptionParameter exceptionParameter) {
		if (exceptionParameter == null) {
			throw new IllegalArgumentException("Exception parameter cannot be null.");
		}
		exceptionTypes.add(exceptionParameter);
	}
	
	public Collection<Class<?>> listExceptionTypes() {
		Set<Class<?>> exceptions = new HashSet<Class<?>>();
		
		
		for (ExceptionParameter param : listExceptionParameters()) {
			exceptions.add(param.getExpectedResponseType());
		}
		return exceptions;
		
		
	}
	protected Collection<ExceptionParameter> listExceptionParameters() {
		return exceptionTypes;
	}
	
	public void clearExceptionTypes() {
		exceptionTypes.clear();
	}
	
	
	
	
	public boolean isHandleExceptionAsString() {
		return handleExceptionAsString;
	}


	public void setHandleExceptionAsString(boolean handleExceptionAsString) {
		this.handleExceptionAsString = handleExceptionAsString;
	}
	
	public void setTimeout (long timeoutValue) {
		
		addFeature(new ServiceTimeout(timeoutValue));
	}
	
	
	public Response invoke() throws ServiceInvokeException {
		if (getServiceData() == null) {
			throw new IllegalArgumentException("Service data has not been set.");
		}
		BansheeDispatcher dispatcher = getServiceData().getDispatcher();
		
		String requestObject = buildRequestObject();
		
		InvocationContext context = buildInvocationContext(dispatcher, requestObject);
		
		for (Feature feature : getFeatureSet()) {
			feature.install(context);
		}
		
		try {
			
			
			dispatcher = context.getDispatcher();
			
			String response = dispatcher.invoke(context.getRequestData());
			Response responseObject =  buildResponseObject(response);
			return responseObject;
		}
		catch (Exception ex) {
			try {
				
				return buildExceptionResponse(ex);
				
			} catch (Exception e) {
				throw new ServiceInvokeException(ex);
			}
		}
	}
	
	
	protected abstract InvocationContext buildInvocationContext (BansheeDispatcher dispatcher, String requestObject);
	
	public Response invokeOneWay() throws ServiceInvokeException {
		BansheeDispatcher dispatcher = getServiceData().getDispatcher();
		
		String requestData = buildRequestObject();
		
		
		InvocationContext context = buildInvocationContext(dispatcher, requestData);
		
		try {
			for (Feature feature : getFeatureSet()) {
				feature.install(context);
			}
			
			dispatcher.invokeOneWay(context.getRequestData());
			
			return null;
		}
		catch (Exception ex) {
			try {
				return buildExceptionResponse(ex);
				
			} catch (Exception e) {
				throw new ServiceInvokeException(ex);
			}
		}
	}
	
	
	protected abstract String buildRequestObject();
	
	protected abstract Response buildResponseObject(String responseData);
	
	protected abstract Response buildExceptionResponse (Exception ex) throws Exception;
}
