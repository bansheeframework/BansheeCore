package com.bansheeproject.engine;

import com.bansheeproject.engine.converters.ObjectConverter;
import com.bansheeproject.exceptions.BansheeUncheckedException;


/**
 * Provides a shell for exceptions.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class ExceptionParameter extends ServiceParameter{

	private Class<?> expectedResponseType;
	
	
	
	private ObjectConverter converter;
	
	private Object exception;
	
	public ExceptionParameter(Class<?> expectedResponseType,
			ObjectConverter converter) {		
		this.expectedResponseType = expectedResponseType;
		this.converter = converter;
		
	}

	@Override
	public Object decode() {
		if (exception == null) {
			throw new BansheeUncheckedException("This method should not be called directly. Must use resolveResponse first.");	
		}
		return exception;
	}

	@Override
	public String encode() {
		throw new BansheeUncheckedException("This method should not be called upon an instance of Exception Parameter.");		
	}

	@Override
	public ServiceParameter resolveResponse(String response)  {
		
		
			Object decoded = converter.decode(response, expectedResponseType);
			ExceptionParameter exceptionParameter = new ExceptionParameter(expectedResponseType, converter);
			exceptionParameter.exception = decoded;
			return exceptionParameter;
		
		
	}

	public Class<?> getExpectedResponseType() {
		return expectedResponseType;
	}
	
	

}
