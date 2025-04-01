package com.DeskBooking.DeskBooking.repository;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DeskBooking.DeskBooking.model.WorkingUnit;

@Repository
@Transactional
public interface WorkingUnitsRepository extends JpaRepository<WorkingUnit, Long> {
	WorkingUnit findByUnitName(String unitName);
	Optional<WorkingUnit> findById(Long id);
	
	@Query("select count(w.id) from WorkingUnit w where w.parkingMap != null")
	Long findByMaps();
}
