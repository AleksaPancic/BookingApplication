package com.DeskBooking.DeskBooking.Controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DeskBooking.DeskBooking.Exceptions.DeskAlredyBookedException;
import com.DeskBooking.DeskBooking.Exceptions.DeskNotAvailableException;
import com.DeskBooking.DeskBooking.Exceptions.DeskNotFoundException;
import com.DeskBooking.DeskBooking.Exceptions.OfficeNotFoundException;
import com.DeskBooking.DeskBooking.Exceptions.ParkingAlreadyBookedException;
import com.DeskBooking.DeskBooking.Exceptions.ParkingNotAvailableException;
import com.DeskBooking.DeskBooking.Exceptions.ParkingNotFoundException;
import com.DeskBooking.DeskBooking.Exceptions.ScheduleNotFoundException;
import com.DeskBooking.DeskBooking.Exceptions.UserNotFoundException;
import com.DeskBooking.DeskBooking.Exceptions.WorkingUnitNotFoundException;
import com.DeskBooking.DeskBooking.Models.Desks;
import com.DeskBooking.DeskBooking.Models.Offices;
import com.DeskBooking.DeskBooking.Models.Parking;
import com.DeskBooking.DeskBooking.Models.ParkingSchedules;
import com.DeskBooking.DeskBooking.Models.Schedules;
import com.DeskBooking.DeskBooking.Models.WorkingUnits;
import com.DeskBooking.DeskBooking.Repositories.DesksRepository;
import com.DeskBooking.DeskBooking.Repositories.OfficesRepository;
import com.DeskBooking.DeskBooking.Repositories.ParkingRepository;
import com.DeskBooking.DeskBooking.Repositories.ParkingSchedulesRepository;
import com.DeskBooking.DeskBooking.Repositories.SchedulesRepository;
import com.DeskBooking.DeskBooking.Repositories.UsersRepository;
import com.DeskBooking.DeskBooking.Repositories.WorkingUnitsRepository;
import com.DeskBooking.DeskBooking.Services.ParkingServiceImpl;
import com.DeskBooking.DeskBooking.Services.SchedulesServiceImpl;

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
	public List<WorkingUnits> getAllWorkingUnits() {
		return workingUnitsRepository.findAll();
	}
	
	//return the list of offices based on the workingUnit name, ex. path: /booking/Beograd
	@GetMapping("/{unit}")
	public List<Offices> getOfficesByWorkingUnitName(@PathVariable final String unit) {
		WorkingUnits workingUnit = workingUnitsRepository.findByUnitName(unit + " Office");
		if (workingUnit == null) {
			throw new WorkingUnitNotFoundException(unit);
		}
		List<Offices> offices = officesRepository.findByWorkingUnit(workingUnit);
		return offices;
	}
	
	//return the list of desks based on the workingUnit name and office id, ex. path: /booking/Beograd/1
	@GetMapping("/{unit}/{office}")
	public List<Desks> getDesksByOfficeName(@PathVariable final String unit, @PathVariable final Long office) {
		WorkingUnits workingUnit = workingUnitsRepository.findByUnitName(unit + " Office");
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
		List<Desks> desks = desksRepository.findByOffice(pomOffices);
		return desks;
	}
	
	
	//save desk reservation in database, path: /booking/reservation
	//params: dateFrom, dateTo, username, deskName
	@PostMapping("/reservation")
	public List<Schedules> setScheduleDate(@RequestBody ScheduleData form) {
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
	public List<Schedules> getAllSchedulesByDeskName(@RequestBody ScheduleData form) {
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
	public void deleteScheduleById(@RequestBody ScheduleData id) {
		Optional<Schedules> schedulesOptional = schedulesRepository.findById(id.getId());
		if (!schedulesOptional.isPresent()) {
			throw new ScheduleNotFoundException(id.getId());
		}
		schedulesService.disableSchedule(id.getId());
	}
	
	//PARKING RESERVATION
	
	@GetMapping("/parking/{unit}")
	public List<Parking> getParking(@PathVariable final String unit) {
		WorkingUnits workingUnit = workingUnitsRepository.findByUnitName(unit);
		if (workingUnit == null) {
			throw new WorkingUnitNotFoundException(unit);
		}
		List<Parking> parking = parkingRepository.findByWorkingUnit(workingUnit);
		return parking;
	}

	@PostMapping("/parking/schedules") //Done
	public List<ParkingSchedules> getAllSchedulesByParkingName(@RequestBody ScheduleParkingData form) {
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
	public List<ParkingSchedules> setScheduleDateParking(@RequestBody ScheduleParkingData form) {
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
			Optional<ParkingSchedules> scheduleOptional = Optional.ofNullable(
					parkingSchedulesRepository.checkParkingSchedule(timestamp, timestamp1, form.getParkingName()));
			if(scheduleOptional.isPresent()) {
				throw new ParkingAlreadyBookedException(form.getDateFrom(), form.getDateTo());
			}
			
			ParkingSchedules parkingschedules = parkingService.saveSchedules(new ParkingSchedules(true, timestamp ,timestamp1 ,
					usersRepository.findByUsername(form.getUsername()), parkingRepository.findByName(form.getParkingName())));
			
			parkingSchedulesRepository.save(parkingschedules);
		}
		
		return getAllSchedulesByParkingName(form);
		
	}
	
	@PostMapping("/parking/disable") //DONE
	public void deleteParkingScheduleById(@RequestBody ScheduleParkingData id) {
		Optional<ParkingSchedules> schedulesOptional = parkingSchedulesRepository.findById(id.getId());
		if (!schedulesOptional.isPresent()) {
			throw new ScheduleNotFoundException(id.getId());
		}
		parkingSchedulesRepository.disableParkingSchedule(id.getId());
	}

}

@Data
class ScheduleData {
	private String dateFrom;
	private String dateTo;
	private String username;
	private String deskName;
	private Long id;
}
@Data
class ScheduleParkingData{
	private String dateFrom;
	private String dateTo;
	private String username;
	private String parkingName;
	private Long id;
}

