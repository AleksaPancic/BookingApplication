package com.DeskBooking.deskbooking.exception;

public class WorkingUnitNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WorkingUnitNotFoundException(Long id) {
		super("Working unit with id: " + id + " not found");
	}
	
	public WorkingUnitNotFoundException(String name) {
		super("Working unit with name: " + name + " not found");
	}

}
