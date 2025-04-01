package com.DeskBooking.DeskBooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DeskBooking.DeskBooking.model.Parking;
import com.DeskBooking.DeskBooking.service.impl.ParkingServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
public class ParkingController {
	
	private final ParkingServiceImpl parkingService;
	
	@GetMapping("/view/parking/list")
	public ResponseEntity<List<Parking>> getParking(){
		List<Parking> list = parkingService.getParking();
		return new ResponseEntity<List<Parking>>(list, HttpStatus.OK);
	}
}
