package com.Bookingapp.Bookingapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Bookingapp.Bookingapp.Service.appUserService;

@Controller
public class appUserController {
//display list of Users
	
	@Autowired
	private appUserService AppUserService; 
	@GetMapping("/")
	public String viewHomePage(Model model) {
		model.addAttribute("listUsers", AppUserService.getAllUsers());
		return "index";	
	}
}
