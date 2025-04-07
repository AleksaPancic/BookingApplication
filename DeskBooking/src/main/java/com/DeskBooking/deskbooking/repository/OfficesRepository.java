package com.DeskBooking.deskbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.deskbooking.model.Offices;
import com.DeskBooking.deskbooking.model.WorkingUnit;

@Repository
@Transactional(readOnly = true)
public interface OfficesRepository extends JpaRepository<Offices, Long>  {
	List<Offices> findByWorkingUnit(Optional<WorkingUnit> workingUnit);
	List<Offices> findByWorkingUnit(WorkingUnit workingUnit);
	Offices findByName(String name);
	Optional<Offices> findById(Long id);
}
