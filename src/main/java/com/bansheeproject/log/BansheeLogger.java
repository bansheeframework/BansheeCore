package com.bansheeproject.log;


/**
 * A simple logger designed specifically for Banshee.
 * @author Alexandre Saudate
 * @since 1.0
 */
public interface BansheeLogger {

	void debug (String param);
	
	void debug (StringBuilder builder);
	
	void warn (String param);
	
	void info(String param);

	void error(String responseData);

	void fatal(String param);
	
}
