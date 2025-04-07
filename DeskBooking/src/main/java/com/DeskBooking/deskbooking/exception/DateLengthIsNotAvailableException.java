package com.DeskBooking.deskbooking.exception;

public class DateLengthIsNotAvailableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DateLengthIsNotAvailableException(Long num) {
		super("Date length: " + num + " is not available");
	}

}
