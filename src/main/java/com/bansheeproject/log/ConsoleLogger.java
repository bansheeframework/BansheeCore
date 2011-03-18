package com.bansheeproject.log;

public class ConsoleLogger implements BansheeLogger{

	public void debug(String param) {
		System.out.println(param);
	}

	public void debug(StringBuilder builder) {
		debug(builder.toString());
	}

	public void error(String responseData) {
		System.err.println(responseData);
		
	}

	public void fatal(String param) {
		System.err.println(param);		
	}

	public void info(String param) {
		System.out.println(param);
	}

	public void warn(String param) {
		System.out.println(param);
		
	}

}
