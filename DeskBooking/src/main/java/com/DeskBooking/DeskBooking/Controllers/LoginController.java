package com.DeskBooking.DeskBooking.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.DeskBooking.DeskBooking.Services.CustomUserDetailService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/login")
public class LoginController {
	
	private final CustomUserDetailService usersService;
	
	@GetMapping
	public String login() {
		return "login";
	}
/*
	@PostMapping("/reset") 
	public String passwordReset(@RequestBody RegistrationRequest request) {
		String password = request.getPassword();
		String confPassword = request.getConfirmPassword();
		
		if (!password.equals(confPassword)) {
			throw new PasswordsDoNotMatchException();
		}
		return usersService.resetPassword(request.getUsername());
	}
*/
	@GetMapping(path = "confirm")
	public String confirm(@RequestParam("token") String token) {
		return usersService.confirmToken(token);
	}
	
	@GetMapping("/")
	public String app() {
		
		return "app";
	}
	
}
