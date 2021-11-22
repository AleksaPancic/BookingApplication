package com.DeskBooking.DeskBooking.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.Models.Desks;
import com.DeskBooking.DeskBooking.Repositories.DesksRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DesksServiceImpl implements DesksService {

	private final DesksRepository desksRepository;
	
	@Override
	public Desks saveDesk(Desks desk) {
		log.info("Saving desks {} to the database", desk.getName());
		return desksRepository.save(desk);
	}

	@Override
	public Optional<Desks> findById(Long id) {
		return desksRepository.findById(id);
	}

}
