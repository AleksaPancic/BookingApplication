package com.DeskBooking.deskbooking.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.DeskBooking.deskbooking.service.DesksService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.deskbooking.exception.DeskNotFoundException;
import com.DeskBooking.deskbooking.model.Desk;
import com.DeskBooking.deskbooking.repository.DesksRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DesksServiceImpl implements DesksService {

	private final DesksRepository desksRepository;
	
	@Override
	public Desk saveDesk(Desk desk) {
		log.info("Saving desks {} to the database", desk.getName());
		if(desksRepository.findByName(desk.getName()) != null) {
			throw new DeskNotFoundException("Desk already exists " + desk.getName());
		}
		return desksRepository.save(desk);
	}
	
	@Override
	public void changeActivity(String name, Boolean activity) {
		log.info("Changing activity for the desk: " + name);
		Desk desk = desksRepository.findByName(name);
		desk.setAvailable(activity);
	}

	@Override
	public Optional<Desk> findById(Long id) {
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

	public List<Desk> getDesks() {
		return new ArrayList<Desk>(desksRepository.findAll());
	}

}