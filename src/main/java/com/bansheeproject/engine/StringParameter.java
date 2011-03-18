package com.bansheeproject.engine;



/**
 * It is a shell for string parameters/responses.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class StringParameter extends ServiceParameter {

	private String parameter;
	
	public StringParameter(String parameter ) {
		this.parameter = parameter;
	}

	@Override
	public Object decode() {
		return parameter;
	}

	@Override
	public String encode() {
		return parameter;
	}

	@Override
	public ServiceParameter resolveResponse(String response) {
		return new StringParameter(response);
	}
	
	
	
}
