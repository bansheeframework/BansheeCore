package com.bansheeproject.engine.wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import com.bansheeproject.utils.CollectionsUtils;


/**
 * Contains metadata about a WSDL service.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class WSDLService {
	
	
	private QName serviceName;
	
	private List<WSDLPort> ports = new ArrayList<WSDLPort>();
	
	

	public QName getServiceName() {
		return serviceName;
	}

	public WSDLService(QName serviceName) {
		super();
		this.serviceName = serviceName;
	}

	public List<WSDLPort> getPorts() {
		return ports;
	}

	public void setPorts(List<WSDLPort> ports) {
		this.ports = ports;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("WSDL Service - ");
		builder.append("Service name: ").append(serviceName);
		builder.append(" Ports: ").append(CollectionsUtils.collectionAsString(ports));
		return builder.toString();
	}

}
