package com.DeskBooking.DeskBooking.Exceptions;

public class RefreshTokenException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public RefreshTokenException() {
		super("Refresh token is missing");
	}

	public RefreshTokenException(String message) {
		super(message);
	}

	
}
