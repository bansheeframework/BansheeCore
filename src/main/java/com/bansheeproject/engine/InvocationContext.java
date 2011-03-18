package com.bansheeproject.engine;

import com.bansheeproject.WebServicesInvocationData;

/**
 * Holds data for a given invocation type.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class InvocationContext {
	
	
	private WebServicesInvocationData  invocationData;
	
	
	private BansheeDispatcher dispatcher;
	
	private String requestData;
	

	public InvocationContext(WebServicesInvocationData invocationData, BansheeDispatcher dispatcher, String requestData) {
		this.invocationData = invocationData;
		this.dispatcher = dispatcher;
		this.requestData = requestData;
		
	}
	public WebServicesInvocationData getInvocationData() {
		return invocationData;
	}
	
	public BansheeDispatcher getDispatcher() {
		return dispatcher;
	}
	
	public String getRequestData() {
		return requestData;
	}
	public void setInvocationData(WebServicesInvocationData invocationData) {
		this.invocationData = invocationData;
	}
	public void setDispatcher(BansheeDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

}
