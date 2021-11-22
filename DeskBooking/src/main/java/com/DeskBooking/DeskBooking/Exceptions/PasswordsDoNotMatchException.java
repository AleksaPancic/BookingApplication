package com.DeskBooking.DeskBooking.Exceptions;

public class PasswordsDoNotMatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PasswordsDoNotMatchException() {
		super("New password and confirm password don't match.");
	}
	
	public PasswordsDoNotMatchException(String message) {
			super(message);
		}

}
