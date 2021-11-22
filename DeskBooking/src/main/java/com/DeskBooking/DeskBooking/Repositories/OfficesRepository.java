package com.DeskBooking.DeskBooking.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.Models.Offices;
import com.DeskBooking.DeskBooking.Models.WorkingUnits;

@Repository
@Transactional(readOnly = true)
public interface OfficesRepository extends JpaRepository<Offices, Long>  {
	List<Offices> findByWorkingUnit(Optional<WorkingUnits> workingUnit);
	List<Offices> findByWorkingUnit(WorkingUnits workingUnit);
	Optional<Offices> findById(Long id);
}
