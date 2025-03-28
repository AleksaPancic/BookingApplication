package com.DeskBooking.DeskBooking.exception;

public class DeskAlredyBookedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DeskAlredyBookedException(final String dateFrom, final String dateTo) {
		super("Appointment: from " + dateFrom + " to " + dateTo + " is alredy booked!");
	}

}
