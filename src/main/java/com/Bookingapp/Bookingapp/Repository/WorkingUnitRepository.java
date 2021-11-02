package com.Bookingapp.Bookingapp.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Bookingapp.Bookingapp.Models.WorkingUnit;

@Repository
@Transactional(readOnly = true)
public interface WorkingUnitRepository extends JpaRepository<WorkingUnit, Long> {

}
