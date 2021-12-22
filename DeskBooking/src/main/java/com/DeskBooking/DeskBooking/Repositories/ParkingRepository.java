package com.DeskBooking.DeskBooking.Repositories;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DeskBooking.DeskBooking.Models.Parking;
import com.DeskBooking.DeskBooking.Models.WorkingUnits;

@Repository
@Transactional(readOnly = true)
public interface ParkingRepository extends JpaRepository<Parking, Long> {
	Optional<Parking> findById(Long id);
	List<Parking> findByWorkingUnit(WorkingUnits workingunit);
	Parking findByName(String name);
}
