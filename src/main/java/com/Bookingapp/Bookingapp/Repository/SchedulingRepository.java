package com.Bookingapp.Bookingapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Bookingapp.Bookingapp.Models.Scheduling;

@Repository
@Transactional(readOnly = true)
public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

}
