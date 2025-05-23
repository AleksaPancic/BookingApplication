package com.DeskBooking.deskbooking.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.DeskBooking.deskbooking.controller.request.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DeskBooking.deskbooking.DTO.AnalyticDeskInformation;
import com.DeskBooking.deskbooking.DTO.AnalyticOfficeInformation;
import com.DeskBooking.deskbooking.DTO.AnalyticParkingInformation;
import com.DeskBooking.deskbooking.DTO.AnalyticParkingScheduleInformation;
import com.DeskBooking.deskbooking.DTO.AnalyticScheduleInformation;
import com.DeskBooking.deskbooking.DTO.TopMostUsedDesks;
import com.DeskBooking.deskbooking.DTO.TopMostUsedOffices;
import com.DeskBooking.deskbooking.exception.DateLengthIsNotAvailableException;
import com.DeskBooking.deskbooking.model.Desk;
import com.DeskBooking.deskbooking.model.Offices;
import com.DeskBooking.deskbooking.model.Parking;
import com.DeskBooking.deskbooking.repository.SchedulesRepository;
import com.DeskBooking.deskbooking.repository.WorkingUnitsRepository;
import com.DeskBooking.deskbooking.service.impl.CustomUserDetailService;
import com.DeskBooking.deskbooking.service.impl.DesksServiceImpl;
import com.DeskBooking.deskbooking.service.impl.OfficesServiceImpl;
import com.DeskBooking.deskbooking.service.impl.ParkingServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	
	private final CustomUserDetailService usersService;
	private final DesksServiceImpl desksService;
	private final OfficesServiceImpl officesService;
	private final ParkingServiceImpl parkingService;
	private final SchedulesRepository schedulesRepository;
	private final WorkingUnitsRepository workingUnitsRepository;

	//USER OPTIONS 
	@PostMapping("/activate/users")
	ResponseEntity<?> setActiveUser(@RequestBody UpdateFormRequest form){
		usersService.ChangeActivityForUser(form.getUsername(), true);
		return ResponseEntity.ok().build();

	}
	
	@PostMapping("/disable/users")
	ResponseEntity<?> setDisableUser(@RequestBody UpdateFormRequest form){
		usersService.ChangeActivityForUser(form.getUsername(), false);
		return ResponseEntity.ok().build();

	}
	
	//DESKS OPTIONS
	
	@PostMapping("/activate/desks")
	ResponseEntity<?> setActiveDesk(@RequestBody DeskDataRequest form){
		desksService.changeActivity(form.getName(), true);
		return ResponseEntity.ok().build();
		
	}
	
	@PostMapping("/disable/desks")
	ResponseEntity<?> setDisableDesk(@RequestBody DeskDataRequest form){
		desksService.changeActivity(form.getName(), false);
		return ResponseEntity.ok().build();

	}
	
	@PostMapping("/add/desks")
	ResponseEntity<?> addDesk(@RequestBody Desk desk){
		desksService.saveDesk(desk);
		return ResponseEntity.ok().build();

	}
	
	@PostMapping("/remove/desks")
	ResponseEntity<?> removeDesk(@RequestBody DeskDataRequest form){
		desksService.removeDesk(form.getName());
		return ResponseEntity.ok().build();

	}
	//OFFICE OPTIONS
	
	@PostMapping("/activate/offices")
	ResponseEntity<?> setActiveOffice(@RequestBody OfficeDataRequest form){
		officesService.changeActivity(form.getName(), true);
		return ResponseEntity.ok().build();
	
	}
	
	@PostMapping("/disable/offices")
	ResponseEntity<?> setDisableOffice(@RequestBody OfficeDataRequest form){
		officesService.changeActivity(form.getName(), false);
		return ResponseEntity.ok().build();
		
	}
	
	@PostMapping("/add/offices")
	ResponseEntity<?> addOffice(@RequestBody Offices office){
		officesService.saveOffice(office);
		return ResponseEntity.ok().build();
	
	}
	
	@PostMapping("/remove/offices")
	ResponseEntity<?> removeOffice(@RequestBody OfficeDataRequest form){
		//officesService.removeSchedule(form.getName());
		officesService.removeDesks(form.getName());
		officesService.removeOffice(form.getName());
		return ResponseEntity.ok().build();
	}
	
	//PARKING OPTIONS
	
	@PostMapping("/activate/parking")
	ResponseEntity<?> setActiveParking(@RequestBody ParkingDataRequest form){
		parkingService.changeActivity(form.getName(), true);
		return ResponseEntity.ok().build();
	
	}
	
	@PostMapping("/disable/parking")
	ResponseEntity<?> setDisableParking(@RequestBody ParkingDataRequest form){
		parkingService.changeActivity(form.getName(), false);
		return ResponseEntity.ok().build();
		
	}
	
	@PostMapping("/add/parking")
	ResponseEntity<?> addParking(@RequestBody Parking parking){
		parkingService.saveParking(parking);
		return ResponseEntity.ok().build();
	
	}
	
	@PostMapping("/remove/parking")
	ResponseEntity<?> removeParking(@RequestBody ParkingDataRequest form){
		parkingService.removeParking(form.getName());
		return ResponseEntity.ok().build();
	}
	
	//ANALYTICS OPTIONS
	//return number of total, regular, canceled and avg schedules per working unit
	//params: dateLength: 30, 7 or 1
	@PostMapping("/anaylitic/schedules")
	public List<AnalyticScheduleInformation> getAnayliticSchedules(@RequestBody DateInfoRequest dateLength) {
		LocalDate myDateFrom = LocalDate.now();
		LocalDate myDateTo = LocalDate.now();
		LocalTime minLocalTime = LocalTime.MIN; 
		LocalTime maxLocalTime = LocalTime.MAX;
		if (dateLength.getDateLength()  == 30) {
			myDateFrom = myDateFrom.minusMonths(1L);
		} else if(dateLength.getDateLength()  == 7) {
			myDateFrom = myDateFrom.minusWeeks(1L);
		} else if (dateLength.getDateLength()  == 1) {
			myDateFrom = myDateFrom.minusDays(1L);
		} else {
			throw new DateLengthIsNotAvailableException(dateLength.getDateLength() );
		}
		LocalDateTime myDateTimeFrom = LocalDateTime.of(myDateFrom, minLocalTime);
		LocalDateTime myDateTimeTo = LocalDateTime.of(myDateTo, maxLocalTime);
		Timestamp timestampFrom = Timestamp.valueOf(myDateTimeFrom);
		Timestamp timestampTo = Timestamp.valueOf(myDateTimeTo);
		Long id = 1L;
		List<AnalyticScheduleInformation> list = new ArrayList<AnalyticScheduleInformation>();
		while (id <= workingUnitsRepository.count()) {
			AnalyticScheduleInformation analytic = schedulesRepository.getAnayliticSchedules(timestampFrom, timestampTo, id);
			Float avg = schedulesRepository.getAvgSchedules(timestampFrom, timestampTo, id);
			analytic.setAvgDailyReservation(avg/dateLength.getDateLength() );
			list.add(analytic);
			id++;
		}
		return list;
	}
	
	//return number of enabled and disable offices per working unit
	@PostMapping("/anaylitic/offices")
	public List<AnalyticOfficeInformation> getAnalyticOffices() {
		List<AnalyticOfficeInformation> list = new ArrayList<AnalyticOfficeInformation>();
		Long id = 1L;
		while(id <= workingUnitsRepository.count()) {
			list.add(schedulesRepository.getAnalyticOffices(id));
			id++;
		}
		
		return list;
	}
	
	//return number of enabled and disable desks per working unit
	@PostMapping("/anaylitic/desks")
	public List<AnalyticDeskInformation> getAnalyticDesks() {
		List<AnalyticDeskInformation> list = new ArrayList<AnalyticDeskInformation>();
		Long id = 1L;
		while(id <= workingUnitsRepository.count()) {
			list.add(schedulesRepository.getAnalyticDesks(id));
			id++;
		}
		
		return list;
	}
	
	//return list of top used offices per working unit
	//params: dateLength: 30, 7 or 1
	@PostMapping("/anaylitic/top/offices")
	public List<List<TopMostUsedOffices>> getTopUsedOffices(@RequestBody DateInfoRequest dateLength) {
		LocalDate myDateFrom = LocalDate.now();
		LocalDate myDateTo = LocalDate.now();
		LocalTime minLocalTime = LocalTime.MIN; 
		LocalTime maxLocalTime = LocalTime.MAX;
		if (dateLength.getDateLength()  == 30) {
			myDateFrom = myDateFrom.minusMonths(1L);
		} else if(dateLength.getDateLength()  == 7) {
			myDateFrom = myDateFrom.minusWeeks(1L);
		} else if (dateLength.getDateLength()  == 1) {
			myDateFrom = myDateFrom.minusDays(1L);
		} else {
			throw new DateLengthIsNotAvailableException(dateLength.getDateLength() );
		}
		LocalDateTime myDateTimeFrom = LocalDateTime.of(myDateFrom, minLocalTime);
		LocalDateTime myDateTimeTo = LocalDateTime.of(myDateTo, maxLocalTime);
		Timestamp timestampFrom = Timestamp.valueOf(myDateTimeFrom);
		Timestamp timestampTo = Timestamp.valueOf(myDateTimeTo);
		List<List<TopMostUsedOffices>> list = new ArrayList<List<TopMostUsedOffices>>();
		Long id = 1L;
		while(id <= workingUnitsRepository.count()) {
			Pageable paging = PageRequest.of(0, 5);
			List<TopMostUsedOffices> pageListOfOffices = schedulesRepository.getTopUsedOffices(timestampFrom, timestampTo, id, paging);
			list.add(pageListOfOffices);
			id++;
		}
		
		return list;
	}
	
	//return list of top used desks per working unit
	//params: dateLength: 30, 7 or 1
	@PostMapping("/anaylitic/top/desks")
	public List<List<TopMostUsedDesks>> getTopUsedDesks(@RequestBody DateInfoRequest dateLength) {
		LocalDate myDateFrom = LocalDate.now();
		LocalDate myDateTo = LocalDate.now();
		LocalTime minLocalTime = LocalTime.MIN; 
		LocalTime maxLocalTime = LocalTime.MAX;
		if (dateLength.getDateLength() == 30) {
			myDateFrom = myDateFrom.minusMonths(1L);
		} else if(dateLength.getDateLength()  == 7) {
			myDateFrom = myDateFrom.minusWeeks(1L);
		} else if (dateLength.getDateLength()  == 1) {
			myDateFrom = myDateFrom.minusDays(1L);
		} else {
			throw new DateLengthIsNotAvailableException(dateLength.getDateLength());
		}
		LocalDateTime myDateTimeFrom = LocalDateTime.of(myDateFrom, minLocalTime);
		LocalDateTime myDateTimeTo = LocalDateTime.of(myDateTo, maxLocalTime);
		Timestamp timestampFrom = Timestamp.valueOf(myDateTimeFrom);
		Timestamp timestampTo = Timestamp.valueOf(myDateTimeTo);
		List<List<TopMostUsedDesks>> list = new ArrayList<List<TopMostUsedDesks>>();
		Long id = 1L;
		while(id <= workingUnitsRepository.count()) {
			Pageable paging = PageRequest.of(0, 5);
			List<TopMostUsedDesks> pageListOfDesks =schedulesRepository.getTopUsedDesks(timestampFrom, timestampTo, id, paging);
			list.add(pageListOfDesks);
			id++;
		}
		
		return list;
	}
	
	//return number of total, regular, canceled, avg schedules and enabled and disabled parking per working unit
	//params: dateLength: 30, 7 or 1
	@PostMapping("/anaylitic/parking")
	public List<AnalyticParkingScheduleInformation> getAnayliticParkingSchedules(@RequestBody DateInfoRequest dateLength) {
		LocalDate myDateFrom = LocalDate.now();
		LocalDate myDateTo = LocalDate.now();
		LocalTime minLocalTime = LocalTime.MIN; 
		LocalTime maxLocalTime = LocalTime.MAX;
		if (dateLength.getDateLength()  == 30) {
			myDateFrom = myDateFrom.minusMonths(1L);
		} else if(dateLength.getDateLength()  == 7) {
			myDateFrom = myDateFrom.minusWeeks(1L);
		} else if (dateLength.getDateLength()  == 1) {
			myDateFrom = myDateFrom.minusDays(1L);
		} else {
			throw new DateLengthIsNotAvailableException(dateLength.getDateLength() );
		}
		LocalDateTime myDateTimeFrom = LocalDateTime.of(myDateFrom, minLocalTime);
		LocalDateTime myDateTimeTo = LocalDateTime.of(myDateTo, maxLocalTime);
		Timestamp timestampFrom = Timestamp.valueOf(myDateTimeFrom);
		Timestamp timestampTo = Timestamp.valueOf(myDateTimeTo);
		Long id = 1L;
		List<AnalyticParkingScheduleInformation> list = new ArrayList<AnalyticParkingScheduleInformation>();
		Long numOfWorkinUnit = workingUnitsRepository.findByMaps();
		while (id <= numOfWorkinUnit) {
			AnalyticParkingScheduleInformation analytic = schedulesRepository.getAnayliticParkingSchedules(timestampFrom, timestampTo, id);
			Float avg = schedulesRepository.getAvgParkingSchedules(timestampFrom, timestampTo, id);
			analytic.setAvgDailyReservation(avg/dateLength.getDateLength());
			AnalyticParkingInformation analyticParking = schedulesRepository.getAnalyticParking(id);
			analytic.setNumOfEnableParkings(analyticParking.getNumOfEnableParkings());
			analytic.setNumOfDisableParkings(analyticParking.getNumOfDisableParkings());
			list.add(analytic);
			id++;
		}
		return list;
	}
		
	
}

