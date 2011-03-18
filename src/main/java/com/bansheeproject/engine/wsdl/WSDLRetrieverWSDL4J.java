package com.bansheeproject.engine.wsdl;

import java.util.List;
import java.util.Map;

import javax.wsdl.Binding;
import javax.wsdl.BindingOperation;
import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.xml.namespace.QName;

import com.bansheeproject.exceptions.WSDLRetrievalException;
import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;


/**
 * An implementation of {@link WSDLRetriever} using the framework
 * WSDL4J.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class WSDLRetrieverWSDL4J implements WSDLRetriever {

	
	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(WSDLRetrieverWSDL4J.class);
	
	public WSDLData retrieveData(String address) throws WSDLRetrievalException {
		
		WSDLData returnData = new WSDLData();
		try {
			
			
			
			Definition def = WSDLFactory.newInstance().newWSDLReader(). readWSDL(address);
			
			Map<QName, Service> services = def.getServices();
			
			returnData.setTargetNamespace(def.getTargetNamespace());
			
			for (Service service : services.values()) {
				WSDLService wsdlService = new WSDLService(service.getQName());
				returnData.getServices().add(wsdlService);
				
				Map<String, Port> ports = service.getPorts();
				for (Port port : ports.values()) {
					WSDLPort wsdlPort = new WSDLPort(port.getName());
					wsdlService.getPorts().add(wsdlPort);
				}
				
			}
			
			Map<QName, Binding> bindings = def.getAllBindings();
			
			for (Binding binding : bindings.values()) {
				List<BindingOperation> bindingOperations = binding.getBindingOperations();
				for (BindingOperation bindingOperation : bindingOperations) {
					String operationName = bindingOperation.getOperation().getName();
					WSDLOperation operation = new WSDLOperation();
					operation.setName(operationName);
					operation.setNamespace(returnData.getTargetNamespace());
					returnData.getOperations().add(operation);
					
				}
			}
			
			return returnData;
			
		} catch (WSDLException e) {
			logger.warn("WSDLException occurred. Exception data: ".concat(e.getMessage()));
			throw new WSDLRetrievalException("Could not retrieve wsdl in address " + address, e);
		}
		
	}
	
	@Override
	public String toString() {
		
		return "WSDL Retriever - WSDL4J version";
	}

}
