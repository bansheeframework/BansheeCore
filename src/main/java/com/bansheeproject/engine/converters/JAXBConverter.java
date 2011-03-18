package com.bansheeproject.engine.converters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.bansheeproject.exceptions.ConverterException;
import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;

/**
 * A converter based on JAXB. It is the default converter of the
 * framework.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class JAXBConverter extends ObjectConverter{

	
	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(JAXBConverter.class);
	
	@Override
	public Object decode(String response, Class<?> expectedType) {
		logger.debug(new StringBuilder("Decoding response: ").append(response));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			JAXBContext context = getJAXBContext(expectedType);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			

			Reader reader = new StringReader(response);
			
			Object responseObject = unmarshaller.unmarshal(reader);			
			logger.debug("Response: ".concat(responseObject.toString()));
			return responseObject;
		}
		catch (ClassCastException ex) {
			logger.warn("ClassCastException: ".concat(ex.getMessage()));
			throw new ConverterException("Expected response type is different than the actual response", ex);
		} catch (ConverterException ex) {
			logger.warn("ConverterException: ".concat(ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			if (! (ex instanceof ConverterException)) {
				logger.warn("Exception: ".concat(ex.getMessage()));
				throw new ConverterException(ex);
			}
			throw (ConverterException)ex;
		}
		finally {
			try {
				baos.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}		
	}

	@Override
	public String encodeImpl(Object source) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			JAXBContext context = getJAXBContext(source);
			Marshaller marshaller = context.createMarshaller();
						
			marshaller.marshal(source, baos);

			String encoded = new String(baos.toByteArray());
			

			return encoded;
		} catch (ConverterException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ConverterException(ex);
		}
		finally {
			try {
				baos.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	protected JAXBContext getJAXBContext(Class<? extends Object> clazz) {
		try {
			logger.debug(new StringBuilder("Retrieving JAXBContext for class ").append(clazz));
			JAXBContext context = JAXBContext.newInstance(new Class[]{clazz}, Collections.<String, Object>emptyMap());
			logger.debug(new StringBuilder("Context created: ").append(context));
			return context;
		} catch (JAXBException e) {
			logger.warn("Exception happened when building JAXBContext.");
			logger.warn("Exception data: ".concat(e.getMessage()));
			logger.warn("Throwing converter exception...");
			throw new ConverterException(e);
		}
	}
	
	private JAXBContext getJAXBContext(Object source) {
		return getJAXBContext(source.getClass());
	}

}
