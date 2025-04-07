package com.DeskBooking.deskbooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DeskBooking.deskbooking.model.Offices;
import com.DeskBooking.deskbooking.service.impl.OfficesServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/offices")
public class OfficeController {
	
	private final OfficesServiceImpl officesService;
	
	@GetMapping("/view/offices/list")
	public ResponseEntity<List<Offices>> getOffices(){
	 List<Offices> list = officesService.getOffices();
	 return new ResponseEntity<List<Offices>>(list, HttpStatus.OK);
	}
}
