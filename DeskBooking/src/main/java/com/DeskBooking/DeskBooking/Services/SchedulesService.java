package com.DeskBooking.DeskBooking.Services;

import com.DeskBooking.DeskBooking.DTO.ShortScheduleInformation;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface SchedulesService {
	//methods: set, get, delete, update, schedule, etc.
    List<ShortScheduleInformation> getAllSchedulesShort(String username);
    List<ShortScheduleInformation> getFromToSchedulesShort(String user, Date from, Date to);
    void disableSchedule(Long id);
}
