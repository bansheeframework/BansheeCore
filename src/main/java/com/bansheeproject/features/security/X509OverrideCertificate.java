package com.bansheeproject.features.security;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509KeyManager;


/**
 * A custom implementation of {@link X509KeyManager}, 
 * that delegates alias choosing for a callback.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class X509OverrideCertificate implements X509KeyManager {
	
	
	private CertificateCallback certificateCallback;
	
	private X509KeyManager delegate;
	
	
	

	public X509OverrideCertificate(CertificateCallback certificateCallback,
			X509KeyManager delegate) {
		super();
		this.certificateCallback = certificateCallback;
		this.delegate = delegate;
	}

	public String chooseClientAlias(String[] arg0, Principal[] arg1, Socket arg2) {
		
		return certificateCallback.chooseClientAlias(arg0, arg1, arg2, delegate);
	}

	public String chooseServerAlias(String arg0, Principal[] arg1, Socket arg2) {
		return certificateCallback.chooseServerAlias(arg0, arg1, arg2, delegate);
	}

	public X509Certificate[] getCertificateChain(String arg0) {

		return delegate.getCertificateChain(arg0);
	}

	public String[] getClientAliases(String arg0, Principal[] arg1) {
		
		return delegate.getClientAliases(arg0, arg1);
	}

	public PrivateKey getPrivateKey(String arg0) {

		return delegate.getPrivateKey(arg0);
	}

	public String[] getServerAliases(String arg0, Principal[] arg1) {

		return delegate.getServerAliases(arg0, arg1);
	}

}
