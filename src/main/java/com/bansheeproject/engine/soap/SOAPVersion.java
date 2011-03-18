package com.bansheeproject.engine.soap;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;

/**
 * Contains specific constants for handling SOAP messages (according
 * to their version).
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public enum SOAPVersion {

	ONEDOTONE {
		@Override
		public String getProtocol() {
			
			return SOAPConstants.SOAP_1_1_PROTOCOL;
		}

		@Override
		public String getNamespace() {

			return "http://schemas.xmlsoap.org/soap/envelope/";
		}

		@Override
		public String getDoNotNeedToUnderstandAttribute() {
			
			return "0";
		}

		@Override
		public String getMustUnderstandAttribute() {

			return "1";
		}
		
		private QName faultQName = new QName("detail"); 
		
		@Override
		public QName getFaultQName() {
		
			return faultQName;
		}
		
	}, ONEDOTTWO {
		@Override
		public String getProtocol() {
			return SOAPConstants.SOAP_1_2_PROTOCOL;
		}

		@Override
		public String getNamespace() {

			return "http://www.w3.org/2003/05/soap-envelope";
		}

		@Override
		public String getDoNotNeedToUnderstandAttribute() {

			return "false";
		}

		@Override
		public String getMustUnderstandAttribute() {
			
			return "true";
		}
		
		private QName faultQName = new QName("http://www.w3.org/2003/05/soap-envelope", "Detail");
		
		@Override
		public QName getFaultQName() {
			
			return faultQName;
		}
	};
	
	public abstract String getProtocol() ;
	
	public abstract String getNamespace();
	
	public abstract String getMustUnderstandAttribute ();
	
	public abstract String getDoNotNeedToUnderstandAttribute();
	
	public abstract QName getFaultQName() ;

	
}
