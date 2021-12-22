package com.DeskBooking.DeskBooking.Repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DeskBooking.DeskBooking.Models.WorkingUnits;

@Repository
@Transactional
public interface WorkingUnitsRepository extends JpaRepository<WorkingUnits, Long> {
	WorkingUnits findByUnitName(String unitName);
	Optional<WorkingUnits> findById(Long id);
	
	@Query("select count(w.id) from WorkingUnits w where w.parkingMap != null")
	Long findByMaps();
}
