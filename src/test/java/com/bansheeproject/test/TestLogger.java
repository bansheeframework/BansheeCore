package com.bansheeproject.test;

import java.util.ArrayList;
import java.util.List;

import com.bansheeproject.log.BansheeLogger;

public class TestLogger implements BansheeLogger {

	public List<String> debugMessages = new ArrayList<String>();
	public List<String> infoMessages = new ArrayList<String>();
	public List<String> warnMessages = new ArrayList<String>();
	public List<String> errorMessages = new ArrayList<String>();
	public List<String> fatalMessages = new ArrayList<String>();
	
	public void debug(String arg0) {
		debugMessages.add(arg0);

	}

	
	public void error(String arg0) {
		errorMessages.add(arg0);

	}

	public void info(String arg0) {
		infoMessages.add(arg0);

	}


	public void warn(String arg0) {
		warnMessages.add(arg0);

	}


	public void fatal(String param) {
		fatalMessages.add(param);
		
	}


	public void debug(StringBuilder builder) {
		debug(builder.toString());
		
	}


}
