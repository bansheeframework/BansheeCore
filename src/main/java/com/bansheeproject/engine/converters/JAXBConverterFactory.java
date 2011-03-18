package com.bansheeproject.engine.converters;

import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;


/**
 * A factory for JAXB converters.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class JAXBConverterFactory extends ObjectConverterFactory{

	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(JAXBConverterFactory.class);
	
	@Override
	public ObjectConverter newObjectConverter() {
		logger.info("Initializing a new instance of JAXBConverter...");
		JAXBConverter converter = new JAXBConverter();
		logger.debug(new StringBuilder("Returning: ").append(converter));
		return converter;
	}

}
