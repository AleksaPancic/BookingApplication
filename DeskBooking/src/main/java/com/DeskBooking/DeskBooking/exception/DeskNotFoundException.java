package com.DeskBooking.DeskBooking.exception;

public class DeskNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DeskNotFoundException(Long id) {
		super("Desk with id: " + id + " not found");
	}
	
	public DeskNotFoundException(String name) {
		super("Desk with name: " + name + " not found");
	}

}
