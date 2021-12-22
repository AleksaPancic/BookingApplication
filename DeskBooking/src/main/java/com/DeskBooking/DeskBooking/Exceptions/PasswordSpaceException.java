package com.DeskBooking.DeskBooking.Exceptions;

public class PasswordSpaceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PasswordSpaceException()  {
		 
			super("Space is not supported");
	}
		
		public PasswordSpaceException(String message) {
				super(message);
			}
	

}
