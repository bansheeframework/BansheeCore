package com.bansheeproject;

import com.bansheeproject.log.BansheeLogFactory;
import com.bansheeproject.log.BansheeLogger;


/**
 * Represents a HTTP header.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 *
 */
public class HttpHeader {
	
	private String header;
	private String content;

	private static BansheeLogger logger = BansheeLogFactory.getDefaultLogger(HttpHeader.class);
	
	
	public HttpHeader(String header, String content) {
		super();
		logger.debug(new StringBuilder("Creating HTTP Header with header ").append(header).append(" and content ").append(content).toString());
		this.header = header;
		this.content = content;
	}


	public String getHeader() {
		return header;
	}


	public void setHeader(String header) {
		this.header = header;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	@Override
	public String toString() {
		
		return header + ":" + content;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HttpHeader other = (HttpHeader) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		return true;
	}
	
	
	
	
	

}
