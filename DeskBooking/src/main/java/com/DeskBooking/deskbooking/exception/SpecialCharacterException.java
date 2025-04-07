package com.DeskBooking.deskbooking.exception;

public class SpecialCharacterException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SpecialCharacterException() 
		 {
		 
			super("Add special character");
	}
		
		public SpecialCharacterException(String message) {
				super(message);
			}
	

}
