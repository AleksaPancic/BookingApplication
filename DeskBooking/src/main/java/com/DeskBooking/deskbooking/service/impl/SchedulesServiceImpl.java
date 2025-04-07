package com.DeskBooking.deskbooking.service.impl;

import com.DeskBooking.deskbooking.DTO.ShortScheduleInformation;
import com.DeskBooking.deskbooking.model.Schedules;

import com.DeskBooking.deskbooking.service.SchedulesService;
import org.springframework.stereotype.Service;

import com.DeskBooking.deskbooking.repository.SchedulesRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class SchedulesServiceImpl implements SchedulesService {
	
	private final SchedulesRepository schedulesRepository;

	@Override
	public List<ShortScheduleInformation> getAllSchedulesShort(String username) {
		return schedulesRepository.getAllSchedulesShortFromUser(username);
	}

	@Override
	public List<ShortScheduleInformation> getFromToSchedulesShort(String user, Date from, Date to) {
		return schedulesRepository.getFromToSchedulesFromUser(user, from, to);
	}

	@Override
	public void disableSchedule(Long id) {
		schedulesRepository.disableSchedule(id);
	}

	@Override
	public Schedules saveSchedules(Schedules schedule) {
		return schedulesRepository.save(schedule);
	}
}
