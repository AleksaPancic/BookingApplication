package com.Bookingapp.Bookingapp.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bookingapp.Bookingapp.Models.RegistrationRequest;
import com.Bookingapp.Bookingapp.Service.RegistrationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class UserRegistrationController {
	private final RegistrationService registrationService = new RegistrationService();
//method to register
	public String register(@RequestBody RegistrationRequest request) {
		return registrationService.register(request);
	}
}
