package com.DeskBooking.DeskBooking.exception;

public class InvalidInputException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidInputException() {
		super("Invalid input");
		
	}

	public InvalidInputException(String message) {
		super(message);
	}
	
	
 
}
