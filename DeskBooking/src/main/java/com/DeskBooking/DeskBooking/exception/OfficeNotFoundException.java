package com.DeskBooking.DeskBooking.exception;

public class OfficeNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OfficeNotFoundException(Long id) {
		super("Office with id: " + id + " not found");
	}
	
	public OfficeNotFoundException(String name) {
		super("Office with name: " + name + " not found");
	}
 
}
