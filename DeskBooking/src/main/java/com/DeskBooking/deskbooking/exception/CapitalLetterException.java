package com.DeskBooking.deskbooking.exception;

public class CapitalLetterException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CapitalLetterException() 
		 {
		 
			super("At least one capital letter required");
	}
		
		public CapitalLetterException(String message) {
				super(message);
			}
	

}
