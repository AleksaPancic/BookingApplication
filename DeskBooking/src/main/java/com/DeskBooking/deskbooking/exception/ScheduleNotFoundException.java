package com.DeskBooking.deskbooking.exception;

public class ScheduleNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ScheduleNotFoundException(final Long id) {
		super("Appointment with id " + id + " not found!");
	}

}
