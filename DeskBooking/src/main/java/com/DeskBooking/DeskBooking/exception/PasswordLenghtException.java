package com.DeskBooking.DeskBooking.exception;

public class PasswordLenghtException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PasswordLenghtException() {
		 
			super("Password must be between 8 and 30 characters long");
	}
		
		public PasswordLenghtException(String message) {
				super(message);
			}
	

}



