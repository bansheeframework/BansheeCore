package com.bansheeproject.features;

import java.util.Calendar;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Node;

import com.bansheeproject.SOAPInvocationData;
import com.bansheeproject.WebServicesInvocationData;
import com.bansheeproject.engine.InvocationContext;
import com.bansheeproject.exceptions.InstallFeatureException;
import com.bansheeproject.utils.DateTimeUtils;
import com.bansheeproject.utils.TimeUnit;

/**
 * Places timestamps over SOAP requests.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class WSTimestampFeature implements WSSecurityFeature{

	private long modifier;

	private TimeUnit unit;
	
	private String timestampId;
	
	private boolean mustUnderstand;
	
	public WSTimestampFeature(long modifier, TimeUnit unit) {
		this(modifier, unit, "Timestamp-1");
	}
	public WSTimestampFeature(long modifier, TimeUnit unit, String timestampId) {
		this(modifier, unit, timestampId, false);
	}
	
	public WSTimestampFeature(long modifier, TimeUnit unit, String timestampId, boolean mustUnderstand) {
		if (modifier <= 0) {
			throw new IllegalArgumentException("Interval cannot be lower or equal than zero.");
		}
		if (unit == null) {
			throw new IllegalArgumentException("Time unit cannot be null.");
		}
		
		this.timestampId = timestampId;
		this.modifier = modifier;
		this.unit = unit;

		this.mustUnderstand = mustUnderstand;
	}
	
	
	
	public void install(InvocationContext context)
			throws InstallFeatureException {
		try {
			WebServicesInvocationData webServicesInvocationData = context.getInvocationData();
			if (!(webServicesInvocationData instanceof SOAPInvocationData)) {
				throw new InstallFeatureException("This feature can only be added to a SOAP environment.");
			}

			SOAPInvocationData soapInvocationData  = (SOAPInvocationData)webServicesInvocationData;

			
			//SOAPMessage message = (SOAPMessage)context.getRequestData();
			SOAPMessage message = null;
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


			if (securityElement.getChildElements(WS_TIMESTAMP_ELEMENT).hasNext()) {
				
				Node child = (Node)securityElement.getChildElements(WS_TIMESTAMP_ELEMENT).next(); 
				
				securityElement.removeChild(child);
				
			}

			SOAPElement wsTimestampElement = securityElement.addChildElement(WS_TIMESTAMP_ELEMENT);

			SOAPElement created = wsTimestampElement.addChildElement(new QName(WS_SECURITY_UTILITY_NAMESPACE, "Created"));
			Calendar calendar = Calendar.getInstance();
			created.setValue(DateTimeUtils.getXMLTimestamp(calendar.getTime()));


			SOAPElement expires = wsTimestampElement.addChildElement(new QName(WS_SECURITY_UTILITY_NAMESPACE, "Expires"));
			unit.add(calendar, (int)modifier);
			expires.setValue(DateTimeUtils.getXMLTimestamp(calendar.getTime()));

		}
		catch (SOAPException ex) {
			throw new InstallFeatureException(ex);
		}
	}
	
	
	@Override
	public String toString() {
		
		return "WS-Security timestamp feature";
	}
	


}
