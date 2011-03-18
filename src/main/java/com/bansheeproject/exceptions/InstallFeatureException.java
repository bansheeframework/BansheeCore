package com.bansheeproject.exceptions;

/**
 * Thrown when a feature cannot be installed in the context.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class InstallFeatureException extends BansheeUncheckedException {

	public InstallFeatureException() {
	}

	public InstallFeatureException(String message) {
		super(message);
	}

	public InstallFeatureException(Throwable cause) {
		super(cause);
	}

	public InstallFeatureException(String message, Throwable cause) {
		super(message, cause);
	}

}
