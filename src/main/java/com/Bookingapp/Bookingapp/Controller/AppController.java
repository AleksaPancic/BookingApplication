package com.Bookingapp.Bookingapp.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AppController {
	@GetMapping
	public String testapp() {
		return "Hello Spring Security!";
	}
}
