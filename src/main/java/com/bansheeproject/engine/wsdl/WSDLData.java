package com.bansheeproject.engine.wsdl;

import java.util.ArrayList;
import java.util.List;

import com.bansheeproject.utils.CollectionsUtils;

/**
 * Contains data for a given WSDL.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class WSDLData {
	
	private String targetNamespace;
	
	private List<WSDLService> services = new ArrayList<WSDLService>();
	
	private List<WSDLOperation> operations = new ArrayList<WSDLOperation>();

	public String getTargetNamespace() {
		return targetNamespace;
	}

	public void setTargetNamespace(String targetNamespace) {
		this.targetNamespace = targetNamespace;
	}

	public List<WSDLService> getServices() {
		return services;
	}

	
	public List<WSDLOperation> getOperations() {
		return operations;
	}
	
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("WSDL Data");
		string.append(" Target namespace: ").append(targetNamespace);
		string.append(" Services: ").append(CollectionsUtils.collectionAsString(services));
		return string.toString();
	}
	
	

}
