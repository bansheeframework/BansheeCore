package com.bansheeproject.features;

import com.bansheeproject.engine.BansheeDispatcher;
import com.bansheeproject.engine.InvocationContext;
import com.bansheeproject.exceptions.InstallFeatureException;

/**
 * Overwrites the invocation address for WSDL based services.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class WSDLRealAddressFeature implements Feature {
	
	
	private String address;
	
	public WSDLRealAddressFeature(String newAddress) {
		this.address = newAddress;
	}

	public void install(InvocationContext context)
			throws InstallFeatureException {
		BansheeDispatcher dispatcher = context.getInvocationData().getServiceData().getDispatcher();
		
		dispatcher.changeInvocationAddress(address);
		
		context.setDispatcher(dispatcher);

	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj.getClass().equals(getClass())) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		
		return getClass().hashCode();
	}
	

	@Override
	public String toString() {
		
		return "Service address change feature";
	}

	
	
}
