package com.DeskBooking.DeskBooking.Services;

import org.springframework.beans.factory.annotation.Autowired;

import com.DeskBooking.DeskBooking.Repositories.SchedulesRepository;

public class SchedulesServiceImpl implements SchedulesService {
	
	@Autowired
	private SchedulesRepository schedulesRepository;
}
