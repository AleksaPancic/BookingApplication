package com.DeskBooking.DeskBooking.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.Exceptions.OfficeNotFoundException;
import com.DeskBooking.DeskBooking.Models.Desks;
import com.DeskBooking.DeskBooking.Models.Offices;
import com.DeskBooking.DeskBooking.Models.Schedules;
import com.DeskBooking.DeskBooking.Repositories.DesksRepository;
import com.DeskBooking.DeskBooking.Repositories.OfficesRepository;
import com.DeskBooking.DeskBooking.Repositories.SchedulesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OfficesServiceImpl implements OfficesSevice {
	
	private final OfficesRepository officesRepository;
	private final DesksRepository desksRepository;
	private final SchedulesRepository schedulesRepository;
	
	ArrayList<Desks> desks = new ArrayList<Desks>();
	
	@Override
	public Offices saveOffice(Offices office) {
		log.info("Saving office {} to the database", office.getName());
		return officesRepository.save(office);
	}
	
	@Override
	public void changeActivity(String name, Boolean activity) {
		
		Offices office = officesRepository.findByName(name);		
		
		log.info("Changing activity {} for the office {}" , activity, name);
		List<Desks> desks = desksRepository.findByOffice(officesRepository.findByName(name));
		if(activity == false) {
			log.info("Changing activity to false for all desks inside office " + name);
			for(int i = 0; i < desks.size(); i++) {
				desks.get(i).setAvailable(false);
			} 
		}
		else {
			log.info("Changing activity to true for all desks inside office " + name);
			for(int i = 0; i < desks.size(); i++) {
				desks.get(i).setAvailable(true);
			} 
		}
		if(office == null) {
			throw new OfficeNotFoundException("Office not found: " + name);
		}
		office.setAvailable(activity);
	}
	
	@Override
	public Optional<Offices> findById(Long id) {
		return officesRepository.findById(id);
	}

	@Override
	public void removeOffice(String name) {
		log.info("Removing office from the database: " + name);
		Offices office = new Offices();
		office = officesRepository.findByName(name);
		if(office == null) {
			throw new OfficeNotFoundException("Office not found: " + name);
		}
		officesRepository.delete(officesRepository.findByName(name));	
	}
	@Override
	public void removeDesks(String name) {
		Offices office = new Offices();
		office = officesRepository.findByName(name);
		List<Desks> desks = desksRepository.findByOffice(office);
		
		for(int i = 0; i < desks.size(); i++) 
		{
			List<Schedules> schedules = schedulesRepository.findByDesk(desks.get(i));
			for(int j = 0; j < schedules.size(); j++) {
				log.warn("Removing schedule with id: " + schedules.get(j).getId());
				schedulesRepository.delete(schedules.get(j));
		}
		log.warn("Removing desk: " + desks.get(i).getName());
		desksRepository.delete(desks.get(i));
		}
	}
	public List<Offices> getOffices() { 
		
        return new ArrayList<Offices>(officesRepository.findAll());
	}
}
