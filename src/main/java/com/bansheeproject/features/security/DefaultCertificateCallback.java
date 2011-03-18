package com.bansheeproject.features.security;

import java.net.Socket;
import java.security.Principal;

import javax.net.ssl.X509KeyManager;

/**
 * Default implementation of {@link CertificateCallback}. 
 * Delegates invocations to the manager (passed as parameter).
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class DefaultCertificateCallback implements CertificateCallback {

	public String chooseClientAlias(String[] arg0, Principal[] arg1,
			Socket arg2, X509KeyManager keyManager) {

		return keyManager.chooseClientAlias(arg0, arg1, arg2);
	}

	public String chooseServerAlias(String arg0, Principal[] arg1, Socket arg2,
			X509KeyManager keyManager) {
		
		return keyManager.chooseServerAlias(arg0, arg1, arg2);
	}

}
