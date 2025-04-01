package com.DeskBooking.DeskBooking.controller;

import com.DeskBooking.DeskBooking.controller.request.PasswordResetFormRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DeskBooking.DeskBooking.exception.UserNotFoundException;
import com.DeskBooking.DeskBooking.service.impl.CustomUserDetailService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reset")
public class ResetPasswordController {
	
	private final CustomUserDetailService usersService;
	
	@PostMapping
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetFormRequest form) throws UserNotFoundException{
		if(usersService.getUser(form.getUsername()) == null)
		{
			throw new UserNotFoundException("Username not found: " + form.getUsername());
		}
		usersService.resetPassword(form.getUsername());
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(path = "confirm")
	public String confirm(@RequestParam("token") String token) {
		return usersService.confirmToken(token);
	}
	
}
