package com.bansheeproject.features;

import com.bansheeproject.engine.InvocationContext;
import com.bansheeproject.exceptions.InstallFeatureException;

/**
 * Core interface for features.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public interface Feature {

	public void install (InvocationContext context) throws InstallFeatureException;
	
}
