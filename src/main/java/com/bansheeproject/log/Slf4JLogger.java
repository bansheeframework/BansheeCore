package com.bansheeproject.log;

import org.slf4j.Logger;

/**
 * An implementation of {@link BansheeLogger} that delegates
 * calls to the API of SLF4J.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class Slf4JLogger implements BansheeLogger{
	
	private Logger delegateLogger;

	public Slf4JLogger(Logger delegateLogger) {
		this.delegateLogger = delegateLogger;
	}
	
	public void debug(String param) {
		delegateLogger.debug(param);
		
	}

	public void debug(StringBuilder builder) {
		debug(builder.toString());
	}
	
	
	public void error(String responseData) {
		delegateLogger.error(responseData);
		
	}

	public void fatal(String param) {
		
	}

	public void info(String param) {
		delegateLogger.info(param);
	}

	public void warn(String param) {
		delegateLogger.warn(param);
		
	}

}
