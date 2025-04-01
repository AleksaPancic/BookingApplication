package com.DeskBooking.DeskBooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DeskBooking.DeskBooking.model.Desk;
import com.DeskBooking.DeskBooking.service.impl.DesksServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/desk")
@RequiredArgsConstructor
public class DeskController {
	
	private final DesksServiceImpl desksService;
	
	@GetMapping("/view/desks/list")
	public ResponseEntity<List<Desk>> getDesks(){
	 List<Desk> list = desksService.getDesks();
	 return new ResponseEntity<List<Desk>>(list, HttpStatus.OK);
	}
}
