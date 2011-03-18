package com.bansheeproject.engine.wsdl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.bansheeproject.exceptions.WSDLRetrievalException;
import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;
import com.bansheeproject.utils.CollectionsUtils;

/**
 * Contains a set of {@link WSDLRetriever} to decide which one 
 * fits best the need.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class ChainedWSDLRetriever implements WSDLRetriever{

	
	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(ChainedWSDLRetriever.class);
	
	private Set<WSDLRetriever> retrievers = new HashSet<WSDLRetriever>();
	
	public ChainedWSDLRetriever() {
		try {
			Class.forName("javax.wsdl.Definition");
			retrievers.add(new WSDLRetrieverWSDL4J());
		} catch (ClassNotFoundException e) {

			throw new RuntimeException("WSDL4J Library has not been found.", e);
		}
	}
	
	
	public WSDLData retrieveData(String address) throws WSDLRetrievalException {
		logger.debug("Retrieving data for ".concat(address));
		Map<WSDLRetriever, Exception> exceptions = new HashMap<WSDLRetriever, Exception>();
		
		if (retrievers.isEmpty()) {
			logger.warn("Retrievers list is emtpty. Throwing exception...");
			throw new WSDLRetrievalException("No retriever has been found. Try adding WSDL4J library to the classpath.");
		}
		
		for (WSDLRetriever retriever : retrievers) {
			try {
				logger.debug("Appplying retriever ".concat(retriever.toString()));
				WSDLData data = retriever.retrieveData(address);
				
				return data;
			}
			catch (Exception ex) {
				exceptions.put(retriever, ex);
			}
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (WSDLRetriever retriever : exceptions.keySet()) {
			builder.append(retriever.getClass().getCanonicalName());
			builder.append("-> Exception data: ").append(exceptions.get(retriever).toString());
			builder.append(". ");
		}
		
		throw new WSDLRetrievalException("Could not find any suitable retriver for address " + address + " - Tried : " + builder.toString());
		
		
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("ChainedWSDLRetriever - ");
		builder.append(CollectionsUtils.collectionAsString(retrievers));
		return builder.toString();
	}
	

}
