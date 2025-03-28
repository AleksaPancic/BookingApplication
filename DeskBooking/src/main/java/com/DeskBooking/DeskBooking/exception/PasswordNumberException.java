package com.DeskBooking.DeskBooking.exception;

public class PasswordNumberException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PasswordNumberException() {
	
	 
		 
			super("Number required");
	}
		
		public PasswordNumberException(String message) {
				super(message);
			}
	

}