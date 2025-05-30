package com.DeskBooking.deskbooking.repository;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DeskBooking.deskbooking.model.Parking;
import com.DeskBooking.deskbooking.model.WorkingUnit;

@Repository
@Transactional(readOnly = true)
public interface ParkingRepository extends JpaRepository<Parking, Long> {
	Optional<Parking> findById(Long id);
	List<Parking> findByWorkingUnit(WorkingUnit workingunit);
	Parking findByName(String name);
}
