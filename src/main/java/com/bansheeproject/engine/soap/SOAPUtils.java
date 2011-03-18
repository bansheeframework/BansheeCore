package com.bansheeproject.engine.soap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.soap.SOAPFaultException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.bansheeproject.exceptions.BansheeMessageParsingException;
import com.bansheeproject.exceptions.BansheeUncheckedException;

/**
 * Contains some support methods for handling SOAP messages.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class SOAPUtils {

	public static SOAPMessage getMessage (String body, SOAPVersion version, String... headers ) {
		String stringMessage = buildEnvelope(body, version, headers);

		ByteArrayInputStream bais = new ByteArrayInputStream(stringMessage.getBytes());

		try {
			SOAPMessage message = MessageFactory.newInstance(version.getProtocol()).createMessage(new MimeHeaders(), bais);
			return message;
		} catch (Exception e) {
			throw new BansheeMessageParsingException(e);
		} 
		finally {
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private static String getNodeAsString (Node node) {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			Source source = new DOMSource(node);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(baos);



			transformer.transform(source, result);
			return new String (baos.toByteArray());
		}
		catch (Exception ex) {
			throw new BansheeUncheckedException(ex);
		}

	}
	
	public static String getMessageBodyContent (SOAPMessage message) throws IOException {	
				
				Node node;
				try {
					node = message.getSOAPBody().getFirstChild();
				} catch (SOAPException e) {
					throw new BansheeUncheckedException(e);
				}
				return getNodeAsString(node);
				
	
	}
	public static String assembleMessage (String sourceBody, SOAPVersion version) throws SOAPException, IOException {
		SOAPMessage message = getMessage(sourceBody, version);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		message.writeTo(baos);
		return new String (baos.toByteArray());
	}
	
	private static String getBodyContent(String envelope) {
		StringBuilder builder = new StringBuilder(envelope);
		builder.delete(0, builder.indexOf("Body>") + "Body>".length());
		
		builder.delete(builder.lastIndexOf("</"), builder.length());
		builder.delete(builder.lastIndexOf("</"), builder.length());
		
		return builder.toString();
		
	}
	
	private static boolean isMessageContainedInEnvelope (String envelope, SOAPVersion version) {
		
		
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(envelope.getBytes()));
			
			Node first = document.getChildNodes().item(0);
			
			String localName = first.getNodeName();
			if (localName.contains(":")) {
				localName = localName.substring(localName.indexOf(":") + 1);
			}

			
			
			return localName.equals("Envelope");

		}
		catch (Exception ex) {
			return false;
		}
	}
	
	public static String buildEnvelope (String body, SOAPVersion version, String... headers ) {
		StringBuilder builder = new StringBuilder();
		builder.append("<soap:Envelope xmlns:soap=\"");
		builder.append(version.getNamespace());
		builder.append("\">");
		builder.append("<soap:Header>");
		for (String header : headers) {
			builder.append(header);
		}
		builder.append("</soap:Header>");
		builder.append("<soap:Body>");
		builder.append(body);
		builder.append("</soap:Body>");
		builder.append("</soap:Envelope>");
		
		return builder.toString();
	}

	public static String getFaultData(SOAPFaultException responseException, SOAPVersion version) throws Exception {
		
		ByteArrayOutputStream baos = null;
		
		try {
		
			Node n = responseException.getFault().getDetail().getFirstChild();
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			
			
			DOMSource domSource = new DOMSource(n);
			StringWriter stringWriter = new StringWriter();
			StreamResult result = new StreamResult(stringWriter);
			
			transformer.transform(domSource, result);
			
			return stringWriter.getBuffer().toString();
			
		} catch (Exception ex) {
			throw ex;
		}
		finally {
			if (baos != null) {
				baos.close();
			}
		}
		
	}
	
	public static String getFaultData(String fullFault, SOAPVersion version) {
		try {
			if (!isMessageContainedInEnvelope(fullFault, version)) {
				return fullFault;
			}
			
			SOAPMessage message = MessageFactory.newInstance(version.getProtocol()).createMessage(new MimeHeaders(), new ByteArrayInputStream(fullFault.getBytes()));

			QName qname = version.getFaultQName();
			Iterator<Node> iterator = message.getSOAPBody().getFault().getChildElements(qname);
			while (iterator.hasNext()) {
				Node detail = iterator.next();
				return getNodeAsString(detail.getFirstChild());
				
			}
			
			return null;
		}
		catch (Exception ex) {
			throw new BansheeUncheckedException(ex);
		}
	}
	
	
	
	
	
	
	public static SOAPMessage buildMessage (String sourceMessage, SOAPVersion version) throws IOException, SOAPException {
		SOAPMessage message = MessageFactory.newInstance(version.getProtocol()).createMessage(new MimeHeaders(), new ByteArrayInputStream(sourceMessage.getBytes()));
		return message;
	}
	
	public static String getMessageContent (SOAPMessage message) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			message.writeTo(baos);
			return new String(baos.toByteArray());
		}
		catch (Exception ex) {
			throw new BansheeUncheckedException(ex);
		}
	}
	
	/*
	
	public static void main(String[] args) throws Exception {
		String testeString = "      <wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
         "<wsu:Timestamp wsu:Id=\"Timestamp-2\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">" +
            "<wsu:Created>2010-11-28T23:56:54.890Z</wsu:Created>" +
            "<wsu:Expires>2010-11-28T23:57:54.890Z</wsu:Expires>" +
         "</wsu:Timestamp>" +
      "</wsse:Security>";

		String message = buildEnvelope("", SOAPVersion.ONEDOTONE);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(message.getBytes());
		SOAPMessage soapMessage = MessageFactory.newInstance(SOAPVersion.ONEDOTONE.getProtocol()).createMessage(new MimeHeaders(), bais);
		
		
			
		soapMessage = appendHeader(soapMessage, testeString, SOAPVersion.ONEDOTONE);
		
		soapMessage.writeTo(System.out);
		
		soapMessage = appendHeader(soapMessage, testeString, SOAPVersion.ONEDOTONE);
		soapMessage.writeTo(System.out);
		
		
		
		
		
	}*/
	
}
