package com.DeskBooking.deskbooking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DeskBooking.deskbooking.exception.PasswordsDoNotMatchException;
import com.DeskBooking.deskbooking.registration.RegistrationRequest;
import com.DeskBooking.deskbooking.registration.RegistrationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/register")
@AllArgsConstructor
public class RegistrationController {
	
	private RegistrationService registrationService;
	
	@PostMapping
	public String register(@RequestBody RegistrationRequest request) throws Exception {
		String password = request.getPassword();
		String confPassword = request.getConfirmPassword();
		
		if (!password.equals(confPassword)) {
			throw new PasswordsDoNotMatchException();
		}
		return registrationService.register(request);
	}
	
	@GetMapping(path = "confirm")
	public String confirm(@RequestParam("token") String token) {
		return registrationService.confirmToken(token);
	}
	
}