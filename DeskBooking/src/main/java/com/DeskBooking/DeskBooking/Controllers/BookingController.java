package com.DeskBooking.DeskBooking.Controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DeskBooking.DeskBooking.Exceptions.OfficeNotFoundException;
import com.DeskBooking.DeskBooking.Exceptions.WorkingUnitNotFoundException;
import com.DeskBooking.DeskBooking.Models.Desks;
import com.DeskBooking.DeskBooking.Models.Offices;
import com.DeskBooking.DeskBooking.Models.WorkingUnits;
import com.DeskBooking.DeskBooking.Repositories.DesksRepository;
import com.DeskBooking.DeskBooking.Repositories.OfficesRepository;
import com.DeskBooking.DeskBooking.Repositories.WorkingUnitsRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("booking")
public class BookingController {
	
	private final WorkingUnitsRepository workingUnitsRepository;
	private final OfficesRepository officesRepository;
	private final DesksRepository desksRepository;
	
	//return a list of workingUnits
	@GetMapping
	public List<WorkingUnits> getAllWorkingUnits() {
		return workingUnitsRepository.findAll();
	}
	
	//return the list of offices based on the workingUnit name, ex: /booking/Beograd Office
	@GetMapping("/{unit}")
	public List<Offices> getOfficesByWorkingUnitName(@PathVariable final String unit) {
		WorkingUnits workingUnit = workingUnitsRepository.findByUnitName(unit);
		if (workingUnit == null) {
			throw new WorkingUnitNotFoundException(unit);
		}
		List<Offices> offices = officesRepository.findByWorkingUnit(workingUnit);
		return offices;
	}
	
	//return the list of desks based on the workingUnit name and office id, ex: /booking/Beograd/1
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
}
