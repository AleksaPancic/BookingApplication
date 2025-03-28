package com.DeskBooking.DeskBooking.service;

import java.util.Optional;

import com.DeskBooking.DeskBooking.model.Parking;
import com.DeskBooking.DeskBooking.model.ParkingSchedules;

public interface ParkingService {
	public Parking saveParking(Parking parking);
	public Optional<Parking> findById(Long id);
	public Parking findByName(String name);
	public void removeParking(String name);
	void changeActivity(String name, Boolean activity);
	ParkingSchedules saveSchedules(ParkingSchedules parkingSchedules);
}
