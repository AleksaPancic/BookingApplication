package com.DeskBooking.DeskBooking.exception;

public class LowercaseLetterException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public LowercaseLetterException() {
		 
			super("At least one lowercase letter required");
	}
		
		public LowercaseLetterException(String message) {
				super(message);
			}
	

}
