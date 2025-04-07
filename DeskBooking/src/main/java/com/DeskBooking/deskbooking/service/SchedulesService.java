package com.DeskBooking.deskbooking.service;

import com.DeskBooking.deskbooking.DTO.ShortScheduleInformation;
import com.DeskBooking.deskbooking.model.Schedules;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface SchedulesService {
	//methods: set, get, delete, update, schedule, etc.
    List<ShortScheduleInformation> getAllSchedulesShort(String username);
    List<ShortScheduleInformation> getFromToSchedulesShort(String user, Date from, Date to);
    void disableSchedule(Long id);
    Schedules saveSchedules(Schedules schedules);
}
