package com.DeskBooking.DeskBooking.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DeskBooking.DeskBooking.Models.Desks;
import com.DeskBooking.DeskBooking.Services.DesksServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/desk")
@RequiredArgsConstructor
public class DeskController {
	
	private final DesksServiceImpl desksService;
	
	@GetMapping("/view/desks/list")
	public ResponseEntity<List<Desks>> getDesks(){
	 List<Desks> list = desksService.getDesks();
	 return new ResponseEntity<List<Desks>>(list, HttpStatus.OK);
	}
}
