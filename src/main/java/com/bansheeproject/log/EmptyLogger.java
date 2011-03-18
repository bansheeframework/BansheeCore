package com.bansheeproject.log;


/**
 * An implementation of {@link BansheeLogger} that does nothing 
 * (acting more like a mock implementation).
 * @author Alexandre Saudate
 * @since 1.0
 */
class EmptyLogger implements BansheeLogger{

	public void error(String responseData) {
		
		
	}

	public void info(String param) {
		
		
	}

	public void debug(String param) {
		
		
	}

	public void debug(StringBuilder builder) {
		
		
	}
	
	public void fatal(String param) {
		
		
	}

	public void warn(String param) {
		
		
	}

	
}
