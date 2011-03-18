package com.bansheeproject;

import com.bansheeproject.engine.BansheeDispatcher;


/**
 * Represents a service location.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public abstract class ServiceData {
	
	
	public abstract BansheeDispatcher getDispatcher ();
	
	public abstract void setAddress(String address);

}
