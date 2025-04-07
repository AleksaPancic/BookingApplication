package com.DeskBooking.deskbooking.service.impl;

import com.DeskBooking.deskbooking.service.WorkingUnitsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.deskbooking.model.WorkingUnit;
import com.DeskBooking.deskbooking.repository.WorkingUnitsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WorkingUnitsServiceImpl implements WorkingUnitsService {
	
	private final WorkingUnitsRepository workingUnitsRepository;
	
	@Override
	public WorkingUnit saveWorkingUnit(WorkingUnit workingUnit) {
		log.info("Saving Working unit {} to the database", workingUnit.getUnitName());
		return workingUnitsRepository.save(workingUnit);
	}
	
}
