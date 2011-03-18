package com.bansheeproject.features.security;

import java.net.Socket;
import java.security.Principal;

import javax.net.ssl.X509KeyManager;


/**
 * Handle alias choosing in certificates.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 * @see DefaultCertificateCallback
 */
public interface CertificateCallback {

	public String chooseClientAlias(String[] arg0, Principal[] arg1, Socket arg2, X509KeyManager keyManager);
	
	public String chooseServerAlias(String arg0, Principal[] arg1, Socket arg2, X509KeyManager keyManager);
	
	
}
