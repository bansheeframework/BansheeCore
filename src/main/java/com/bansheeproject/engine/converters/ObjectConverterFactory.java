package com.bansheeproject.engine.converters;

import org.apache.commons.discovery.tools.DiscoverClass;

import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;


/**
 * An abstract factory for {@link ObjectConverter}.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public abstract class ObjectConverterFactory {
	
	
	public static final String DEFAULT_FACTORY = JAXBConverterFactory.class.getName();
	
	public static final String SYSTEM_PROPERTY = ObjectConverterFactory.class.getCanonicalName();
	
	public abstract ObjectConverter newObjectConverter();	
	
	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(ObjectConverterFactory.class);
	
	public static ObjectConverterFactory newInstance() {
		
		logger.debug("Attempting to initialize commons-discovery discoverer to find instances of ObjectConverterFactory");
		
		DiscoverClass discoverer = new DiscoverClass();
		ObjectConverterFactory instance;
		try {			
			instance = (ObjectConverterFactory)discoverer.newInstance(ObjectConverterFactory.class, System.getProperties(), ObjectConverterFactory.DEFAULT_FACTORY);
			logger.debug(new StringBuilder ("Instance found: ").append(instance.getClass()));
		} catch (Exception e) {
			logger.warn("Could not initialize any instance of ObjectConverterFactory");
			logger.warn("Exception data: ");
			logger.warn(e.getMessage());
			throw new RuntimeException("An exception ocurred when looking for an object converter factory.", e);
		} 
		return instance;
	}
		
		
}
