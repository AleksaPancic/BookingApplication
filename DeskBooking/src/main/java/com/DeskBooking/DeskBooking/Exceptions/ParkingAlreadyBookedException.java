package com.DeskBooking.DeskBooking.Exceptions;

public class ParkingAlreadyBookedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ParkingAlreadyBookedException(String dateFrom, String dateTo) {
		super("Parking already booked from " + dateFrom + " to " + dateTo);
	}

	
}
