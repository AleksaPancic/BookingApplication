package com.DeskBooking.DeskBooking.Services;

import java.util.Optional;

import com.DeskBooking.DeskBooking.Models.Parking;
import com.DeskBooking.DeskBooking.Models.ParkingSchedules;

public interface ParkingService {
	public Parking saveParking(Parking parking);
	public Optional<Parking> findById(Long id);
	public Parking findByName(String name);
	public void removeParking(String name);
	void changeActivity(String name, Boolean activity);
	ParkingSchedules saveSchedules(ParkingSchedules parkingSchedules);
}
