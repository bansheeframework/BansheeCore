package com.bansheeproject.features;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Node;

import com.bansheeproject.SOAPInvocationData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.engine.InvocationContext;
import com.bansheeproject.engine.soap.SOAPUtils;
import com.bansheeproject.engine.soap.SOAPVersion;
import com.bansheeproject.exceptions.InstallFeatureException;
import com.bansheeproject.utils.DateTimeUtils;

/**
 * Implements WS-Security username and password for WSDL-based services.
 * @author Alexandre Saudate
 * @since 1.0
 */
public class WSSecurityUsernamePassword implements WSSecurityFeature{

	private String userName;
	
	private String password;
	
	private PasswordType passwordType;
	
	private boolean mustUnderstand;
	
	private String elementId;
	
	private SOAPVersion version;
	
	public WSSecurityUsernamePassword(String userName, String password, SOAPVersion version) {
		this(userName, password, PasswordType.TEXT, version);
	}
	
	public WSSecurityUsernamePassword(String userName, String password, PasswordType passwordType, SOAPVersion version) {
		this (userName, password, passwordType, false, version);
	}
	
	public WSSecurityUsernamePassword(String userName, String password, PasswordType passwordType, boolean mustUnderstand, SOAPVersion version) {
		this(userName, password, passwordType, mustUnderstand, "UsernameToken-1", version);
	}
	
	public WSSecurityUsernamePassword(String userName, String password, PasswordType passwordType, boolean mustUnderstand, String elementId, SOAPVersion version) {
		this.userName = userName;
		this.password = password;
		this.passwordType = passwordType;
		this.mustUnderstand = mustUnderstand;
		this.elementId = elementId;
		this.version = version;
		
	}
	
	
	public void install(InvocationContext context)
			throws InstallFeatureException {
		
		try {
			WebServicesInvocationData webServicesInvocationData = context.getInvocationData();
			if (!(webServicesInvocationData instanceof SOAPInvocationData)) {
				throw new InstallFeatureException("This feature can only be added to a SOAP environment.");
			}

			SOAPInvocationData soapInvocationData  = (SOAPInvocationData)webServicesInvocationData;
			
			String stringMessage = context.getRequestData();
			SOAPMessage message = SOAPUtils.buildMessage(stringMessage, version);

			
			SOAPElement securityElement = null;

			Iterator it = message.getSOAPHeader().getChildElements(WS_SECURITY_ELEMENT);
			if (it.hasNext()) {
				securityElement = (SOAPElement)it.next();
			}
			else {
				securityElement = message.getSOAPHeader().addChildElement(WS_SECURITY_ELEMENT);
			}

			
			
			if (mustUnderstand)
				securityElement.addAttribute(new QName(soapInvocationData.getVersion().getNamespace(), "mustUnderstand"), soapInvocationData.getVersion().getMustUnderstandAttribute());
			else 
				securityElement.addAttribute(new QName(soapInvocationData.getVersion().getNamespace(), "mustUnderstand"), soapInvocationData.getVersion().getDoNotNeedToUnderstandAttribute());


			if (securityElement.getChildElements(WS_USERNAME_TOKEN_ELEMENT).hasNext()) {
				
				Node child = (Node)securityElement.getChildElements(WS_USERNAME_TOKEN_ELEMENT).next(); 
				
				securityElement.removeChild(child);
				
			}

			SOAPElement tokenElement = securityElement.addChildElement(WS_USERNAME_TOKEN_ELEMENT);
			tokenElement.addAttribute(new QName(WS_SECURITY_UTILITY_NAMESPACE, "Id"), elementId);
			
			SOAPElement userElement = tokenElement.addChildElement(new QName(WS_SECURITY_NAMESPACE, "Username", "wsse"));
			userElement.setTextContent(userName);
			
			SOAPElement passwordElement = tokenElement.addChildElement(new QName(WS_SECURITY_NAMESPACE, "Password", "wsse"));
			passwordElement.addAttribute(new QName("Type"), passwordType.getNamespace());
			
			
			String nonce = getNonce();
			String created = DateTimeUtils.getXMLTimestamp(new Date());
			passwordElement.setValue(passwordType.encode(password, nonce, created));
			
			SOAPElement nonceElement = tokenElement.addChildElement(new QName(WS_SECURITY_NAMESPACE, "Nonce", "wsse"));
			nonceElement.addAttribute(new QName("EncodingType"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
			nonceElement.setValue(new String(new Base64().encode(nonce.getBytes())));
			
			SOAPElement createdElement = tokenElement.addChildElement(new QName(WS_SECURITY_UTILITY_NAMESPACE, "Created", "wsu"));
			createdElement.setValue(created);
			
			
			
		}
		catch (SOAPException ex) {
			throw new InstallFeatureException(ex);
		} catch (IOException e) {

			throw new InstallFeatureException(e);
		}
		
		
	}
	
	
	private String getNonce() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] seed = new byte[16];
		secureRandom.nextBytes(seed);
		
		//Base64 base64 = new Base64();
		//seed = base64.encode(seed);
		
		return new String(seed);
		
		
		
	}
	
	@Override
	public String toString() {
		
		return "WS-Security Username and Password Feature";
	}

}
