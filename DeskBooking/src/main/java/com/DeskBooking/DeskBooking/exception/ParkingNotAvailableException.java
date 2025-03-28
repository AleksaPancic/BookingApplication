package com.DeskBooking.DeskBooking.exception;

public class ParkingNotAvailableException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ParkingNotAvailableException(String parkingName) {
		super("Parking not available " + parkingName);
	}

	
}
