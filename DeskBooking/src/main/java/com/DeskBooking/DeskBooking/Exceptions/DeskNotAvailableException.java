package com.DeskBooking.DeskBooking.Exceptions;

public class DeskNotAvailableException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeskNotAvailableException(String deskName) {
		super("Desk: " + deskName + " is not available!");
	}
}
