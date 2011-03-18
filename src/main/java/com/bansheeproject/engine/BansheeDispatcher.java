package com.bansheeproject.engine;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import com.bansheeproject.exceptions.BansheeInvocationFaultException;
import com.bansheeproject.exceptions.ServiceInvokeException;
import com.bansheeproject.features.security.CertificateCallback;
import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;


/**
 * Represents a dispatcher to a given service.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public abstract class BansheeDispatcher {
	
	
	
	
	public abstract String invoke (String param) throws ServiceInvokeException, BansheeInvocationFaultException ;
	
	public abstract void invokeOneWay (String param) throws ServiceInvokeException, BansheeInvocationFaultException;	
	
	public abstract void setTimeout (long timeout);
	
	public abstract void setCertificateSelectors (String reference, InputStream keyStoreData, String keyStorePassword, InputStream trustStoreData, String trustStorePassword ) throws GeneralSecurityException, IOException;
	
	public abstract void setCertificateSelectors (String reference, InputStream keyStoreData, String keyStorePassword, InputStream trustStoreData, String trustStorePassword , CertificateCallback callback) throws GeneralSecurityException, IOException;
	
	public abstract void changeInvocationAddress (String address) ;
	
	private BansheeLogger logger = BansheeLogFactory.getEmptyLogger();
	
	public void setLogger (BansheeLogger logger) {
		if (logger != null) {
			this.logger = logger;
		}
	}
	
	protected BansheeLogger getLogger() {
		return logger;
	}

}
