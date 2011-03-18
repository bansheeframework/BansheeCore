package com.bansheeproject.test.entities;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(namespace="http://services.test.bansheeproject.com/")
public class StringOperationResponse {

	private String _return;
	

	public String getReturn() {
		return _return;
	}
	
	public void setReturn(String return1) {
		_return = return1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_return == null) ? 0 : _return.hashCode());
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
		StringOperationResponse other = (StringOperationResponse) obj;
		if (_return == null) {
			if (other._return != null)
				return false;
		} else if (!_return.equals(other._return))
			return false;
		return true;
	}
	
	
	
	
}
