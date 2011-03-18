package com.bansheeproject.features;

import org.slf4j.Logger;

import com.bansheeproject.engine.InvocationContext;
import com.bansheeproject.exceptions.InstallFeatureException;
import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;


/**
 * Feature for logging input/output of services.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class LogFeature implements Feature {
	
	private BansheeLogger logger;
	
	private static BansheeLogger bansheeLogger = BansheeLogFactory.getDefaultLogger(LogFeature.class);
	
	
	public LogFeature(BansheeLogger logger) {
		this.logger = logger;
	}

	public LogFeature(Logger logger) {
		this.logger = BansheeLogFactory.getSLF4JLogger(logger);
	}

	public void install(InvocationContext context)
			throws InstallFeatureException {
		bansheeLogger.debug("Installing log feature");
		context.getDispatcher().setLogger(logger);
	}
	
	@Override
	public String toString() {
		
		return "Logging Feature";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		return this.getClass().equals(obj.getClass());
	}
	

}
