package com.DeskBooking.DeskBooking.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.Models.WorkingUnits;
import com.DeskBooking.DeskBooking.Repositories.WorkingUnitsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WorkingUnitsServiceImpl implements WorkingUnitsService {
	
	private final WorkingUnitsRepository workingUnitsRepository;
	
	@Override
	public WorkingUnits saveWorkingUnit(WorkingUnits workingUnits) {
		log.info("Saving Working unit {} to the database", workingUnits.getUnitName());
		return workingUnitsRepository.save(workingUnits);
	}
	
}
