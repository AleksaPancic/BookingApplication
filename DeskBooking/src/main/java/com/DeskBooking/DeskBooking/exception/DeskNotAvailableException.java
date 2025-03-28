package com.DeskBooking.DeskBooking.exception;

public class DeskNotAvailableException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeskNotAvailableException(String deskName) {
		super("Desk: " + deskName + " is not available!");
	}
}
