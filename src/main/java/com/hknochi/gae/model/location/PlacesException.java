package com.hknochi.gae.model.location;

public class PlacesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2087991420249641052L;

	public PlacesException(String message) {
		super(message);
	}

	public PlacesException(String message, Throwable cause) {
		super(message, cause);
	}
}
