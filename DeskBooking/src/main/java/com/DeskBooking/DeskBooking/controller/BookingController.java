package com.DeskBooking.DeskBooking.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.DeskBooking.DeskBooking.controller.request.ScheduleDataRequest;
import com.DeskBooking.DeskBooking.controller.request.ScheduleParkingDataRequest;
import com.DeskBooking.DeskBooking.controller.response.DeskResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DeskBooking.DeskBooking.exception.DeskAlredyBookedException;
import com.DeskBooking.DeskBooking.exception.DeskNotAvailableException;
import com.DeskBooking.DeskBooking.exception.DeskNotFoundException;
import com.DeskBooking.DeskBooking.exception.OfficeNotFoundException;
import com.DeskBooking.DeskBooking.exception.ParkingAlreadyBookedException;
import com.DeskBooking.DeskBooking.exception.ParkingNotAvailableException;
import com.DeskBooking.DeskBooking.exception.ParkingNotFoundException;
import com.DeskBooking.DeskBooking.exception.ScheduleNotFoundException;
import com.DeskBooking.DeskBooking.exception.UserNotFoundException;
import com.DeskBooking.DeskBooking.exception.WorkingUnitNotFoundException;
import com.DeskBooking.DeskBooking.model.Desk;
import com.DeskBooking.DeskBooking.model.Offices;
import com.DeskBooking.DeskBooking.model.Parking;
import com.DeskBooking.DeskBooking.model.ParkingSchedule;
import com.DeskBooking.DeskBooking.model.Schedules;
import com.DeskBooking.DeskBooking.model.WorkingUnit;
import com.DeskBooking.DeskBooking.repository.DesksRepository;
import com.DeskBooking.DeskBooking.repository.OfficesRepository;
import com.DeskBooking.DeskBooking.repository.ParkingRepository;
import com.DeskBooking.DeskBooking.repository.ParkingSchedulesRepository;
import com.DeskBooking.DeskBooking.repository.SchedulesRepository;
import com.DeskBooking.DeskBooking.repository.UsersRepository;
import com.DeskBooking.DeskBooking.repository.WorkingUnitsRepository;
import com.DeskBooking.DeskBooking.service.impl.ParkingServiceImpl;
import com.DeskBooking.DeskBooking.service.impl.SchedulesServiceImpl;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {
	
	private final WorkingUnitsRepository workingUnitsRepository;
	private final OfficesRepository officesRepository;
	private final DesksRepository desksRepository;
	private final ParkingRepository parkingRepository;
	private final SchedulesRepository schedulesRepository;
	private final ParkingSchedulesRepository parkingSchedulesRepository;
	private final UsersRepository usersRepository;
	private final ParkingServiceImpl parkingService;
	private final SchedulesServiceImpl schedulesService;
	
	
	//return a list of workingUnits
	@GetMapping
	public List<WorkingUnit> getAllWorkingUnits() {
		return workingUnitsRepository.findAll();
	}
	
	//return the list of offices based on the workingUnit name, ex. path: /booking/Beograd
	@GetMapping("/{unit}")
	public List<Offices> getOfficesByWorkingUnitName(@PathVariable final String unit) {
		WorkingUnit workingUnit = workingUnitsRepository.findByUnitName(unit + " Office");
		if (workingUnit == null) {
			throw new WorkingUnitNotFoundException(unit);
		}
		List<Offices> offices = officesRepository.findByWorkingUnit(workingUnit);
		return offices;
	}
	
	//return the list of desks based on the workingUnit name and office id, ex. path: /booking/Beograd/1
	@GetMapping("/{unit}/{office}")
	public List<DeskResponse> getDesksByOfficeName(@PathVariable final String unit, @PathVariable final Long office) {
		WorkingUnit workingUnit = workingUnitsRepository.findByUnitName(unit + " Office");
		if (workingUnit == null) {
			throw new WorkingUnitNotFoundException(unit);
		}
		List<Offices> offices = officesRepository.findByWorkingUnit(workingUnit);
		Offices pomOffices = new Offices();
		Integer count = 0;
		for (Offices offices2 : offices) {
			if(offices2.getId().equals(office)) {
				pomOffices = offices2;
				break;
			}
			count++;
		}
		if (count.equals(offices.size()) ) {
			throw new OfficeNotFoundException(office);
		}

		//this should go in Service
		List<Desk> desks = desksRepository.findByOffice(pomOffices);
		List<DeskResponse> deskResponse = desks.stream().map(desk -> {
			DeskResponse response = new DeskResponse();
			response.setName(desk.getName());
			response.setAvailable(desk.getAvailable());
			response.setOffice(desk.getOffice());
			return response;
		}).collect(Collectors.toList());

		return deskResponse;
	}
	
	
	//save desk reservation in database, path: /booking/reservation
	//params: dateFrom, dateTo, username, deskName
	@PostMapping("/reservation")
	public List<Schedules> setScheduleDate(@RequestBody ScheduleDataRequest form) {
		Timestamp timestamp = Timestamp.valueOf(form.getDateFrom());
		Timestamp timestamp1 = Timestamp.valueOf(form.getDateTo());
		if(form.getUsername() != "" && form.getDeskName() != "" && form.getDateFrom() != "" &&  form.getDateTo() != "") {
			if (usersRepository.findByUsername(form.getUsername()).equals(null)) {
				throw new UserNotFoundException(form.getUsername());
			}
			if (desksRepository.findByName(form.getDeskName()).equals(null)) {
				throw new DeskNotFoundException(form.getDeskName());
			}
			if (!desksRepository.findByName(form.getDeskName()).getAvailable()) {
				throw new DeskNotAvailableException(form.getDeskName());
			}
			Optional<Schedules> scheduleOptional = Optional.ofNullable(
					schedulesRepository.checkSchedule(timestamp, timestamp1, form.getDeskName()));
			if(scheduleOptional.isPresent()) {
				throw new DeskAlredyBookedException(form.getDateFrom(), form.getDateTo());
			}
			
			Schedules schedules = schedulesService.saveSchedules(new Schedules(true, timestamp ,timestamp1 ,
					usersRepository.findByUsername(form.getUsername()), desksRepository.findByName(form.getDeskName())));
			
			schedulesRepository.save(schedules);
		}
		
		return getAllSchedulesByDeskName(form);
		
	}

	//return the list of all reservations for required desk and user one months berfore and after current date
	//path: /booking/schedules, params: deskName
	@PostMapping("/schedules")
	public List<Schedules> getAllSchedulesByDeskName(@RequestBody ScheduleDataRequest form) {
		LocalDateTime myDateFrom = LocalDateTime.now();
		LocalDateTime myDateTo = LocalDateTime.now();
		myDateFrom = myDateFrom.minusMonths(1L);
		myDateTo = myDateTo.plusMonths(1L);
		Timestamp timestampFrom = Timestamp.valueOf(myDateFrom);
		Timestamp timestampTo = Timestamp.valueOf(myDateTo);
		
		if(form.getDeskName() != "" && form.getDateFrom() != "" &&  form.getDateTo() != "") {
			if (desksRepository.findByName(form.getDeskName()).equals(null)) {
				throw new DeskNotFoundException(form.getDeskName());
			}
			if (!desksRepository.findByName(form.getDeskName()).getAvailable()) {
				throw new DeskNotAvailableException(form.getDeskName());
			}
		}

		return schedulesRepository.getAllSchedules(timestampFrom , timestampTo, form.getDeskName());
	}

	//delete from fe - set status false in database
	//path: /booking/delete, params: schedluesId
	@PostMapping("/disable")
	public void deleteScheduleById(@RequestBody ScheduleDataRequest id) {
		Optional<Schedules> schedulesOptional = schedulesRepository.findById(id.getId());
		if (!schedulesOptional.isPresent()) {
			throw new ScheduleNotFoundException(id.getId());
		}
		schedulesService.disableSchedule(id.getId());
	}
	
	//PARKING RESERVATION
	
	@GetMapping("/parking/{unit}")
	public List<Parking> getParking(@PathVariable final String unit) {
		WorkingUnit workingUnit = workingUnitsRepository.findByUnitName(unit);
		if (workingUnit == null) {
			throw new WorkingUnitNotFoundException(unit);
		}
		List<Parking> parking = parkingRepository.findByWorkingUnit(workingUnit);
		return parking;
	}

	@PostMapping("/parking/schedules") //Done
	public List<ParkingSchedule> getAllSchedulesByParkingName(@RequestBody ScheduleParkingDataRequest form) {
		LocalDateTime myDateFrom = LocalDateTime.now();
		LocalDateTime myDateTo = LocalDateTime.now();
		myDateFrom = myDateFrom.minusMonths(1L);
		myDateTo = myDateTo.plusMonths(1L);
		Timestamp timestampFrom = Timestamp.valueOf(myDateFrom);
		Timestamp timestampTo = Timestamp.valueOf(myDateTo);
		
		if(form.getParkingName() != "" && form.getDateFrom() != "" &&  form.getDateTo() != "") {
			if (parkingRepository.findByName(form.getParkingName()).equals(null)) {
				throw new ParkingNotFoundException(form.getParkingName());
			}
			if (!parkingRepository.findByName(form.getParkingName()).getAvailable()) {
				throw new ParkingNotAvailableException(form.getParkingName());
			}
		}
		return parkingSchedulesRepository.getAllParkingSchedules(timestampFrom , timestampTo, form.getParkingName());
	}
	
	@PostMapping("/parking/reservation")
	public List<ParkingSchedule> setScheduleDateParking(@RequestBody ScheduleParkingDataRequest form) {
		Timestamp timestamp = Timestamp.valueOf(form.getDateFrom());
		Timestamp timestamp1 = Timestamp.valueOf(form.getDateTo());
		if(form.getUsername() != "" && form.getParkingName() != "" && form.getDateFrom() != "" &&  form.getDateTo() != "") {
			if (usersRepository.findByUsername(form.getUsername()).equals(null)) {
				throw new UserNotFoundException(form.getUsername());
			}
			if (parkingRepository.findByName(form.getParkingName()).equals(null)) {
				throw new ParkingNotFoundException(form.getParkingName());
			}
			if (!parkingRepository.findByName(form.getParkingName()).getAvailable()) {
				throw new ParkingNotAvailableException(form.getParkingName());
			}
			Optional<ParkingSchedule> scheduleOptional = Optional.ofNullable(
					parkingSchedulesRepository.checkParkingSchedule(timestamp, timestamp1, form.getParkingName()));
			if(scheduleOptional.isPresent()) {
				throw new ParkingAlreadyBookedException(form.getDateFrom(), form.getDateTo());
			}
			
			ParkingSchedule parkingschedules = parkingService.saveSchedules(new ParkingSchedule(true, timestamp ,timestamp1 ,
					usersRepository.findByUsername(form.getUsername()), parkingRepository.findByName(form.getParkingName())));
			
			parkingSchedulesRepository.save(parkingschedules);
		}
		
		return getAllSchedulesByParkingName(form);
		
	}
	
	@PostMapping("/parking/disable") //DONE
	public void deleteParkingScheduleById(@RequestBody ScheduleParkingDataRequest id) {
		Optional<ParkingSchedule> schedulesOptional = parkingSchedulesRepository.findById(id.getId());
		if (!schedulesOptional.isPresent()) {
			throw new ScheduleNotFoundException(id.getId());
		}
		parkingSchedulesRepository.disableParkingSchedule(id.getId());
	}

}

