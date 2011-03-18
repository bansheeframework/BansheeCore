package com.bansheeproject.engine.converters;

import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;



/**
 * Represents a converter from objects to string and vice-versa.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public abstract class ObjectConverter {
	
	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(ObjectConverter.class);

	public String encode (Object source) {
		logger.debug("Encoding: ".concat(source.toString()));
		String response = encodeImpl(source);
		if (response.indexOf("?>") != -1) {
			response = response.substring(response.indexOf("?>") + 2);
		}
		logger.debug("Response: ".concat(response));
		return response;
		
	}
	
	protected abstract String encodeImpl (Object source);
	
	public abstract Object decode (String response, Class<?> expectedType);

}
