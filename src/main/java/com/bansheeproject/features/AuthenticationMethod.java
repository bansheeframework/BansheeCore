package com.bansheeproject.features;

/**
 * Contains HTTP authentication types. 
 * @author Alexandre Saudate
 * @since 1.0
 */
public enum AuthenticationMethod {

	BASIC {
		@Override
		public String getMethod() {
			
			return "Basic";
		}
	}, DIGEST {
		@Override
		public String getMethod() {
			
			return "Digest";
		}
	};
	
	
	public abstract String getMethod();
	
}
