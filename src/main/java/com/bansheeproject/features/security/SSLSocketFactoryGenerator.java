package com.bansheeproject.features.security;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;


/**
 * A helper class to generate SSLSocketFactories. 
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class SSLSocketFactoryGenerator {

	
	private static Map<String, SoftReference<SSLSocketFactory>> map = Collections.synchronizedMap(new HashMap<String, SoftReference<SSLSocketFactory>>());
	
	
	public SSLSocketFactory getSSLSocketFactoryInstance (String reference , InputStream keyStore, String keyStorePassword, InputStream trustStore, String trustStorePassword, CertificateCallback callback) throws GeneralSecurityException, IOException {
		
		if (map.containsKey(reference)) {
			
			SoftReference<SSLSocketFactory> socketFactoryRef = map.get(reference);
			SSLSocketFactory socketFactory = socketFactoryRef.get();
			if (socketFactory != null)
				return socketFactory;
		}
		
		
		
		
		KeyManager[] keyManagers = getKeyManagers(keyStore, keyStorePassword);
		TrustManager[] trustManagers = getTrustManagers(trustStore, trustStorePassword);
		
		for (int i = 0; i < keyManagers.length; i++) {
			if (keyManagers[i] instanceof X509KeyManager) {
				keyManagers[i] = new X509OverrideCertificate(callback, (X509KeyManager)keyManagers[i]);
			}
		}
		
		SSLContext context = SSLContext.getInstance("SSL");
		context.init(keyManagers, trustManagers, null);
		
		SSLSocketFactory sslSocketFactory = context.getSocketFactory();
		
		
		SoftReference<SSLSocketFactory> softRef = new SoftReference<SSLSocketFactory>(sslSocketFactory);
		map.put(reference, softRef);
		
		return sslSocketFactory;
		
	}
	
	
	private KeyManager[] getKeyManagers(InputStream keyStoreStream, String keyStorePassword) throws GeneralSecurityException, IOException {
		try {
			KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			
			
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			
			keyStore.load(keyStoreStream, keyStorePassword.toCharArray());
			
			factory.init(keyStore, keyStorePassword.toCharArray());
			
			KeyManager[] managers = factory.getKeyManagers();
			
			return managers;
			
			
		} catch (NoSuchAlgorithmException e) {
			//Should never happen
			throw new RuntimeException(e);
		}
		
	}
	
	private TrustManager[] getTrustManagers(InputStream trustStoreStream, String trustStorePassword) throws GeneralSecurityException, IOException {
		try {
			TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			
			keyStore.load(trustStoreStream, trustStorePassword.toCharArray());
			
			factory.init(keyStore);
			
			TrustManager[] managers = factory.getTrustManagers();
			
			return managers;
			
			
		} catch (NoSuchAlgorithmException e) {
			//Should never happen
			throw new RuntimeException(e);
		}
		
	}
	
	
	
	
	
	
}
