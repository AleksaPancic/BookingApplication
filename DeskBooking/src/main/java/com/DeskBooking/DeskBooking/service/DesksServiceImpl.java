package com.DeskBooking.DeskBooking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.exception.DeskNotFoundException;
import com.DeskBooking.DeskBooking.model.Desks;
import com.DeskBooking.DeskBooking.repository.DesksRepository;

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
		if(desksRepository.findByName(desk.getName()) != null) {
			throw new DeskNotFoundException("Desk already exists " + desk.getName());
		}
		return desksRepository.save(desk);
	}
	
	@Override
	public void changeActivity(String name, Boolean activity) {
		log.info("Changing activity for the desk: " + name);
		Desks desk = desksRepository.findByName(name);
		desk.setAvailable(activity);
	}

	@Override
	public Optional<Desks> findById(Long id) {
		return desksRepository.findById(id);
	}

	@Override
	public void removeDesk(String name) {
		log.info("Removing desk {} from the database: " + name);
		if(desksRepository.findByName(name) == null) {
			throw new DeskNotFoundException("Desk does not exist: " + name);
		}
		desksRepository.delete(desksRepository.findByName(name));
	}

	public List<Desks> getDesks() {
		return new ArrayList<Desks>(desksRepository.findAll());
	}

}