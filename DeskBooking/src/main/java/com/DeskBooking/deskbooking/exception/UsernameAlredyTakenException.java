package com.DeskBooking.deskbooking.exception;

public class UsernameAlredyTakenException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UsernameAlredyTakenException(final String username) {
		super("Username: " + username + " alredy taken!");
	}

}
