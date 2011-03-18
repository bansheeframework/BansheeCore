package com.bansheeproject.engine.wsdl;


/**
 * A factory for {@link WSDLRetriever}.
 * @author Alexandre Saudate
 * @since 1.0
 */
public class WSDLRetrieverFactory {
	
	private static WSDLRetrieverFactory instance = new WSDLRetrieverFactory();
	
	
	private WSDLRetrieverFactory() {
		
	}
	
	public static WSDLRetrieverFactory getInstance() {
		return instance;
	}
	
	public WSDLRetriever getWSDLRetrieverInstance() {
		return new ChainedWSDLRetriever();
	}

}
