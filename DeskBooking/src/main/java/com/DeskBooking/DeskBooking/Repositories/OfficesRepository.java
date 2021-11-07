package com.DeskBooking.DeskBooking.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.Models.Offices;

@Repository
@Transactional(readOnly = true)
public interface OfficesRepository extends JpaRepository<Offices, Long>  {
}
