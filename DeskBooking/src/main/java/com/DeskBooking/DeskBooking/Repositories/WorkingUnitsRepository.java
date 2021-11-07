package com.DeskBooking.DeskBooking.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DeskBooking.DeskBooking.Models.WorkingUnits;

@Repository
public interface WorkingUnitsRepository extends JpaRepository<WorkingUnits, Long> {
	WorkingUnits findByunitName(String unitName);
}
