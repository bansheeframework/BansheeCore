package com.bansheeproject.features;

import javax.xml.namespace.QName;

/**
 * An interface for WS-Security based features.
 * @author Alexandre Saudate
 * @since 1.0
 */
public interface WSSecurityFeature extends Feature{
	
	public static final String WS_SECURITY_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
	
	public static final String WS_SECURITY_UTILITY_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
	
	public static final QName WS_SECURITY_ELEMENT = new QName(WS_SECURITY_NAMESPACE, "Security", "wsse");
	
	public static final QName WS_TIMESTAMP_ELEMENT = new QName(WS_SECURITY_UTILITY_NAMESPACE, "Timestamp");
	
	public static final QName WS_USERNAME_TOKEN_ELEMENT = new QName(WS_SECURITY_NAMESPACE, "UsernameToken", "wsse");
	

	
	

}
