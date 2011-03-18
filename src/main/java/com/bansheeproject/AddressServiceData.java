package com.bansheeproject;

import com.bansheeproject.engine.BansheeDispatcher;
import com.bansheeproject.engine.URLDispatcher;
import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;
import com.bansheeproject.utils.ArrayUtils;



/**
 * It is used to perform an invocation through the
 * address without caring about the invocation protocol.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 *
 */
public class AddressServiceData extends ServiceData{
	
	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(AddressServiceData.class);
	
	
	private String url;
	
	private HttpHeader[] headers;
	
	
	private HttpMethod method;

	public AddressServiceData(String url, HttpHeader... headers) {
		
		
		super();
		logger.debug("Creating an address service data with url: " + url);
		logger.debug("Headers: " + ArrayUtils.parseArrayToString(headers));
		this.url = url;
		this.headers = headers;
		
		
	}


	private URLDispatcher dispatcher ;
	

	
	public BansheeDispatcher getDispatcher() {
		logger.debug(new StringBuilder("Instantiating an URLDispatcher with url ").append(url).append(", HTTP method ").append(getHttpMethod()).append(" and headers ").append(ArrayUtils.parseArrayToString(headers)).toString());

		URLDispatcher dispatcher = new URLDispatcher(url, getHttpMethod(), headers);

		logger.debug(new StringBuilder("Returning dispatcher ").append(dispatcher).toString());
		return dispatcher;
		
		
	}
	
	
	
	public HttpMethod getHttpMethod () {
		return HttpMethod.POST;
	}
	
	@Override
	public void setAddress(String address) {
		this.url = address;
		
	}

}
