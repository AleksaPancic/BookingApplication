package com.DeskBooking.deskbooking.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.DeskBooking.deskbooking.service.ParkingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.deskbooking.exception.ParkingNotFoundException;
import com.DeskBooking.deskbooking.model.Parking;
import com.DeskBooking.deskbooking.model.ParkingSchedule;
import com.DeskBooking.deskbooking.repository.ParkingRepository;
import com.DeskBooking.deskbooking.repository.ParkingSchedulesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ParkingServiceImpl implements ParkingService {

	private final ParkingRepository parkingRepository;
	private final ParkingSchedulesRepository parkingSchedulesRepository;
	
	@Override
	public Parking saveParking(Parking parking) {
		log.info("Saving parking {} to the database", parking.getName());
		if(parkingRepository.findByName(parking.getName()) != null) {
			throw new ParkingNotFoundException("Parking spot already exists " + parking.getName());
		}
		return parkingRepository.save(parking);
	}

	@Override
	public Optional<Parking> findById(Long id) {
		return parkingRepository.findById(id);
	}
	
	@Override
	public Parking findByName(String name) {
		return parkingRepository.findByName(name);
	}

	@Override
	public void removeParking(String name) {
		log.info("Removing parking {} from the database: " + name);
		if(parkingRepository.findByName(name) == null) {
			throw new ParkingNotFoundException("Parking spot does not exist: " + name);
		}
		parkingRepository.delete(parkingRepository.findByName(name));
	}

	@Override
	public void changeActivity(String name, Boolean activity) {
		log.info("Changing activity for parking spot: " + name);
		Parking parking = parkingRepository.findByName(name);
		parking.setAvailable(activity);
	}
	
	public List<Parking> getParking() {
		return new ArrayList<Parking>(parkingRepository.findAll());
	}

	
	@Override
	public ParkingSchedule saveSchedules(ParkingSchedule parkingSchedule) {
		return parkingSchedulesRepository.save(parkingSchedule);
	}
}
