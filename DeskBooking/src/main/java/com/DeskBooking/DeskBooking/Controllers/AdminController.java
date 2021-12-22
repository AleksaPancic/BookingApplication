package com.DeskBooking.DeskBooking.Controllers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DeskBooking.DeskBooking.DTO.AnalyticDeskInformation;
import com.DeskBooking.DeskBooking.DTO.AnalyticOfficeInformation;
import com.DeskBooking.DeskBooking.DTO.AnalyticParkingInformation;
import com.DeskBooking.DeskBooking.DTO.AnalyticParkingScheduleInformation;
import com.DeskBooking.DeskBooking.DTO.AnalyticScheduleInformation;
import com.DeskBooking.DeskBooking.DTO.TopMostUsedDesks;
import com.DeskBooking.DeskBooking.DTO.TopMostUsedOffices;
import com.DeskBooking.DeskBooking.Exceptions.DateLengthIsNotAvailableException;
import com.DeskBooking.DeskBooking.Models.Desks;
import com.DeskBooking.DeskBooking.Models.Offices;
import com.DeskBooking.DeskBooking.Models.Parking;
import com.DeskBooking.DeskBooking.Models.WorkingUnits;
import com.DeskBooking.DeskBooking.Repositories.SchedulesRepository;
import com.DeskBooking.DeskBooking.Repositories.WorkingUnitsRepository;
import com.DeskBooking.DeskBooking.Services.CustomUserDetailService;
import com.DeskBooking.DeskBooking.Services.DesksServiceImpl;
import com.DeskBooking.DeskBooking.Services.OfficesServiceImpl;
import com.DeskBooking.DeskBooking.Services.ParkingServiceImpl;

import lombok.Data;
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
	ResponseEntity<?> setActiveUser(@RequestBody UpdateForm form){
		usersService.ChangeActivityForUser(form.getUsername(), true);
		return ResponseEntity.ok().build();

	}
	
	@PostMapping("/disable/users")
	ResponseEntity<?> setDisableUser(@RequestBody UpdateForm form){
		usersService.ChangeActivityForUser(form.getUsername(), false);
		return ResponseEntity.ok().build();

	}
	
	//DESKS OPTIONS
	
	@PostMapping("/activate/desks")
	ResponseEntity<?> setActiveDesk(@RequestBody DesksData form){
		desksService.changeActivity(form.getName(), true);
		return ResponseEntity.ok().build();
		
	}
	
	@PostMapping("/disable/desks")
	ResponseEntity<?> setDisableDesk(@RequestBody DesksData form){
		desksService.changeActivity(form.getName(), false);
		return ResponseEntity.ok().build();

	}
	
	@PostMapping("/add/desks")
	ResponseEntity<?> addDesk(@RequestBody Desks desk){
		desksService.saveDesk(desk);
		return ResponseEntity.ok().build();

	}
	
	@PostMapping("/remove/desks")
	ResponseEntity<?> removeDesk(@RequestBody DesksData form){
		desksService.removeDesk(form.getName());
		return ResponseEntity.ok().build();

	}
	//OFFICE OPTIONS
	
	@PostMapping("/activate/offices")
	ResponseEntity<?> setActiveOffice(@RequestBody OfficeData form){
		officesService.changeActivity(form.getName(), true);
		return ResponseEntity.ok().build();
	
	}
	
	@PostMapping("/disable/offices")
	ResponseEntity<?> setDisableOffice(@RequestBody OfficeData form){
		officesService.changeActivity(form.getName(), false);
		return ResponseEntity.ok().build();
		
	}
	
	@PostMapping("/add/offices")
	ResponseEntity<?> addOffice(@RequestBody Offices office){
		officesService.saveOffice(office);
		return ResponseEntity.ok().build();
	
	}
	
	@PostMapping("/remove/offices")
	ResponseEntity<?> removeOffice(@RequestBody OfficeData form){
		//officesService.removeSchedule(form.getName());
		officesService.removeDesks(form.getName());
		officesService.removeOffice(form.getName());
		return ResponseEntity.ok().build();
	}
	
	//PARKING OPTIONS
	
	@PostMapping("/activate/parking")
	ResponseEntity<?> setActiveParking(@RequestBody ParkingData form){
		parkingService.changeActivity(form.getName(), true);
		return ResponseEntity.ok().build();
	
	}
	
	@PostMapping("/disable/parking")
	ResponseEntity<?> setDisableParking(@RequestBody ParkingData form){
		parkingService.changeActivity(form.getName(), false);
		return ResponseEntity.ok().build();
		
	}
	
	@PostMapping("/add/parking")
	ResponseEntity<?> addParking(@RequestBody Parking parking){
		parkingService.saveParking(parking);
		return ResponseEntity.ok().build();
	
	}
	
	@PostMapping("/remove/parking")
	ResponseEntity<?> removeParking(@RequestBody ParkingData form){
		parkingService.removeParking(form.getName());
		return ResponseEntity.ok().build();
	}
	
	//ANALYTICS OPTIONS
	//return number of total, regular, canceled and avg schedules per working unit
	//params: dateLength: 30, 7 or 1
	@PostMapping("/anaylitic/schedules")
	public List<AnalyticScheduleInformation> getAnayliticSchedules(@RequestBody DateInfo dateLength) {
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
	public List<List<TopMostUsedOffices>> getTopUsedOffices(@RequestBody DateInfo dateLength) {
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
	public List<List<TopMostUsedDesks>> getTopUsedDesks(@RequestBody DateInfo dateLength) {
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
	public List<AnalyticParkingScheduleInformation> getAnayliticParkingSchedules(@RequestBody DateInfo dateLength) {
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
@Data
class DesksData {
	private String name;
}
@Data 
class OfficeData{
	private String name;
}
@Data
class ParkingData{
	private String name;
}

@Data
class WorkingUnitData {
	private String workingUnitName;
	private Long workingUnitId;
}

@Data
class DateInfo {
	private Long dateLength;
}

