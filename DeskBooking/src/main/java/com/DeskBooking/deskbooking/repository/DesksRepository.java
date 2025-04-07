package com.DeskBooking.deskbooking.repository;

import com.DeskBooking.deskbooking.model.Desk;
import com.DeskBooking.deskbooking.model.Offices;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface DesksRepository extends JpaRepository<Desk, Long> {
	Optional<Desk> findById(Long id);
	List<Desk> findByOffice(Offices office);
	Desk findByName(String name);
}
