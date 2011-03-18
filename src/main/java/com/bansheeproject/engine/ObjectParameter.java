package com.bansheeproject.engine;

import com.bansheeproject.engine.converters.ObjectConverter;

/**
 * Provides a shell for object parameters.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class ObjectParameter extends ServiceParameter {

	private Object parameter;
	
	private Class expectedResponseType;
	
	private ObjectConverter converter;
	
	
	public ObjectParameter(Object objectParameter, Class expectedResponseType, ObjectConverter converter) {
		this.parameter = objectParameter;
		this.expectedResponseType = expectedResponseType;
		this.converter = converter; 
		
	}

	@Override
	public Object decode() {
		
		return parameter;
	}

	@Override
	public String encode() {
		
		String encoded = converter.encode(parameter);		
		return encoded;
	}
	
	 

	@Override
	public ServiceParameter resolveResponse(String response) {
		Object responseObject = this.converter.decode(response, expectedResponseType);
		ObjectParameter parameter = new ObjectParameter(responseObject, expectedResponseType, converter);
		return parameter;
	}

	

}
