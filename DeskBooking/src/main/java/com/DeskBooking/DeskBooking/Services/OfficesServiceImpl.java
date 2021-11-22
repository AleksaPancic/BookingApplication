package com.DeskBooking.DeskBooking.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.Models.Offices;
import com.DeskBooking.DeskBooking.Repositories.OfficesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OfficesServiceImpl implements OfficesSevice {
	
	private final OfficesRepository officesRepository;
	
	@Override
	public Offices saveOffice(Offices office) {
		log.info("Saving office {} to the database", office.getName());
		return officesRepository.save(office);
	}

	@Override
	public Optional<Offices> findById(Long id) {
		return officesRepository.findById(id);
	}

}
