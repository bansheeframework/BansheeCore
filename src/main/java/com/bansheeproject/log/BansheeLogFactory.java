package com.bansheeproject.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * An abstract factory for loggers.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class BansheeLogFactory {
	
	
	public static BansheeLogger getEmptyLogger( ) {
		return new EmptyLogger();
	}
	
	public static BansheeLogger getDefaultLogger(String name) {
		Logger slf4jLogger = LoggerFactory.getLogger(name);
		return new Slf4JLogger(slf4jLogger);
	}
	
	public static BansheeLogger getDefaultLogger (Class<?> clazz) {
		
		return getDefaultLogger(clazz.getSimpleName() );
	}

	public static BansheeLogger getSLF4JLogger(Logger logger) {
		
		return new Slf4JLogger(logger);
	}
	
	
	

}
