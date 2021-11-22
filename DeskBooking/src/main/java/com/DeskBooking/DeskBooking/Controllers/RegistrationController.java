package com.DeskBooking.DeskBooking.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DeskBooking.DeskBooking.Exceptions.PasswordsDoNotMatchException;
import com.DeskBooking.DeskBooking.Registration.RegistrationRequest;
import com.DeskBooking.DeskBooking.Registration.RegistrationService;

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
		
		if(password.equals("")) {
			throw new Exception("Password empty field");
		}
		if(confPassword.equals("")) {
			throw new Exception("ConfPassword Empty field");
		}
		if(confPassword.equals(password)) {
			throw new Exception("OK!");
		}
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