package com.bansheeproject;

import java.io.File;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;

import com.bansheeproject.engine.BansheeDispatcher;
import com.bansheeproject.engine.JAXWSDispatcherImpl;
import com.bansheeproject.engine.wsdl.WSDLData;
import com.bansheeproject.engine.wsdl.WSDLRetriever;
import com.bansheeproject.engine.wsdl.WSDLRetrieverFactory;
import com.bansheeproject.exceptions.BansheeDispatcherCreationException;
import com.bansheeproject.exceptions.WSDLRetrievalException;
import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;


/**
 * It represents service data provided by a WSDL. 
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class WSDLServiceData extends ServiceData {

	private String wsdlLocation;
	
	private String targetNamespace;
	
	private String serviceName;
	
	private String portName;
	
	private BansheeDispatcher cachedDispatcher;
	
	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(WSDLServiceData.class);
	
	public WSDLServiceData(String wsdlLocation, String targetNamespace,
			String serviceName, String portName) {
		this(wsdlLocation, targetNamespace, serviceName, portName, false);
		
	}
	
	public WSDLServiceData(String wsdlLocation, String targetNamespace,
			String serviceName, String portName, boolean eagerFetchWSDL) {
		super();
		StringBuilder builder = new StringBuilder();
		builder.append("Initializing WSDL Service data: WSDLLocation - ");
		builder.append(wsdlLocation);
		builder.append(". Target namespace:");
		builder.append(targetNamespace);
		builder.append(". Service name:").append(serviceName);
		builder.append(". Port name:").append(portName);
		builder.append(". Eager fetch WSDL:").append(eagerFetchWSDL);
		
		logger.debug(builder);
		if (wsdlLocation == null) {
			throw new IllegalArgumentException("WSDL location cannot be null.");
		}
		
		this.wsdlLocation = wsdlLocation;
		this.targetNamespace = targetNamespace;
		this.serviceName = serviceName;
		this.portName = portName;
		if (eagerFetchWSDL) {
			cachedDispatcher = getDispatcher();
		}
	}
	
	public static WSDLServiceData loadFromFile (File f ) throws WSDLRetrievalException {
		
		return loadFromAddress(f.toURI().toASCIIString());
	}
	
	
	public static WSDLServiceData loadFromAddress (String address) throws WSDLRetrievalException{
		logger.debug("Retrieving service data from address: ".concat(address));
		WSDLRetriever retriever = WSDLRetrieverFactory.getInstance().getWSDLRetrieverInstance();
		logger.debug(new StringBuilder("Instance of WSDLRetriever found: ").append(retriever));
		WSDLData data = retriever.retrieveData(address);
		
		String wsdlLocation = address;
		String targetNamespace = data.getTargetNamespace();
		
		if (data.getServices().size() != 1) {
			throw new WSDLRetrievalException("WSDL has more than one service defined, so you have to build the WSDLServiceData manually, using the constructor provided.");			
		}
		
		if (data.getServices().get(0).getPorts().size() != 1) {
			throw new WSDLRetrievalException("Service has more than one port defined, so you have to build the WSDLServiceData manually, using the constructor provided.");
		}
		
		
		String serviceName = data.getServices().get(0).getServiceName().getLocalPart();
		String portName = data.getServices().get(0).getPorts().get(0).getName();
		
		WSDLServiceData serviceData = new WSDLServiceData(wsdlLocation, targetNamespace, serviceName, portName, true);
		return serviceData;
	}
	
	

	@Override
	public BansheeDispatcher getDispatcher() {
		if (cachedDispatcher != null)
			return cachedDispatcher;
		
		try {
			
			URL url = new URL(wsdlLocation);
			
			
			Service service = Service.create(url, new QName(targetNamespace, serviceName));
			
			Dispatch dispatcher = service.createDispatch(new QName(targetNamespace, portName ), SOAPMessage.class, Mode.MESSAGE);
			
			JAXWSDispatcherImpl response = new JAXWSDispatcherImpl(dispatcher ); 
			
			if (address != null) {
				response.changeInvocationAddress(address);
			}
			return response;
		} catch (Exception e) {
			throw new BansheeDispatcherCreationException(e);
		}
		
	}
	
	private String address;
	
	@Override
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
