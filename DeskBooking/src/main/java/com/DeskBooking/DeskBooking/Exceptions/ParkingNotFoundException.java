package com.DeskBooking.DeskBooking.Exceptions;

public class ParkingNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ParkingNotFoundException(Long id) {
		super("Parking spot with id: " + id + " not found.");
	}
	
	public ParkingNotFoundException(String name) {
		super("Parking spot with name: " + name + " not found.");
	}
}
