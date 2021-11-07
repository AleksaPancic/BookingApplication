package com.DeskBooking.DeskBooking.Repositories;

import com.DeskBooking.DeskBooking.Models.Desks;
import com.DeskBooking.DeskBooking.Models.Offices;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface DesksRepository extends JpaRepository<Desks, Long> {
	Optional<Desks> findById(Long id);
	List<Desks> findByOffice(Offices office);
	Desks findByName(String name);
}
