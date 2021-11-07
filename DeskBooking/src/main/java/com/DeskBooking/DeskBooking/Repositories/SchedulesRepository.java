package com.DeskBooking.DeskBooking.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.Models.Schedules;

@Repository
@Transactional(readOnly = true)
public interface SchedulesRepository extends JpaRepository<Schedules, Long>  {

}
