package com.bansheeproject;

import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;
import com.bansheeproject.utils.ArrayUtils;

/**
 * Service data type for HTTP invocations.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class HTTPServiceData extends AddressServiceData {

	
	private HttpMethod method;
	
	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(HTTPServiceData.class);
	
	public HTTPServiceData(String url, HttpMethod method, HttpHeader... headers) {
		super(url, headers);
		logger.debug(new StringBuilder("Creating HTTP Service data with params: URL ").append(url).append(", HTTP method ").append(method).append(" and headers ").append(ArrayUtils.parseArrayToString(headers)).toString() );
		this.method = method;
		
	}
	
	
	@Override
	public HttpMethod getHttpMethod() {
		
		return this.method;
	}

}
