package com.bansheeproject.features;

import java.io.InputStream;

import com.bansheeproject.engine.InvocationContext;
import com.bansheeproject.exceptions.InstallFeatureException;
import com.bansheeproject.features.security.CertificateCallback;


/**
 * Selects alias inside a security certificate.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class SSLAliasSelectorFeature implements Feature {

	
	private CertificateCallback certificateCallback;
	private InputStream keyStore;
	private String keyStorePassword;
	private InputStream trustStore;
	private String trustStorePassword;
	private String reference;
	
	public SSLAliasSelectorFeature(String reference, CertificateCallback callback,
			InputStream keyStore, String keyStorePassword,
			InputStream trustStore, String trustStorePassword) {
		if (reference == null) {
			throw new IllegalArgumentException("Reference to a SSL selector cannot be null.");
		}
		
		this.certificateCallback = callback;
		this.keyStore = keyStore;
		this.keyStorePassword = keyStorePassword;
		this.trustStore = trustStore;
		this.trustStorePassword = trustStorePassword;
		this.reference = reference;
	}


	public void install(InvocationContext context)
			throws InstallFeatureException {
		try {
			context.getDispatcher().setCertificateSelectors(reference, keyStore, keyStorePassword, trustStore, trustStorePassword, certificateCallback);
		}
		catch (Exception ex) {
			throw new InstallFeatureException(ex);
		}
	}
	
	@Override
	public String toString() {
		
		return "SSL Selector Feature";
	}

}
