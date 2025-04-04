package com.DeskBooking.DeskBooking.service.impl;

import com.DeskBooking.DeskBooking.service.WorkingUnitsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.model.WorkingUnit;
import com.DeskBooking.DeskBooking.repository.WorkingUnitsRepository;

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
