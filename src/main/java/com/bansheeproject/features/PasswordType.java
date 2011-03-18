package com.bansheeproject.features;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import com.bansheeproject.exceptions.BansheeUncheckedException;

/**
 * Password types for WS-Security.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public enum PasswordType {

	TEXT {

		@Override
		public String encode(String password, String nonce, String created) {
			
			return password;
		}

		@Override
		public String getNamespace() {
			
			return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";
		}
		
	}, DIGEST {

		@Override
		public String encode(String password, String nonce, String created) {
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("SHA");
				String toDigest = nonce + created + password;
				byte[] data = messageDigest.digest(toDigest.getBytes());
				Base64 base64 = new Base64();
				data = base64.encode(data);
				return new String (data);
			} catch (NoSuchAlgorithmException e) {
				throw new BansheeUncheckedException(e);
			}
			
		}

		@Override
		public String getNamespace() {

			return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest";
		}
		
	};
	
	
	public abstract String encode(String password, String nonce, String created) ;
	
	public abstract String getNamespace() ;
	
}
