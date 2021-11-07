package com.DeskBooking.DeskBooking;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.DeskBooking.DeskBooking.Models.Role;
import com.DeskBooking.DeskBooking.Models.Users;
//import com.DeskBooking.DeskBooking.Models.WorkingUnits;
import com.DeskBooking.DeskBooking.Services.UsersService;
import com.DeskBooking.DeskBooking.Services.WorkingUnitsService;

@SpringBootApplication
public class DeskBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeskBookingApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(UsersService userService, WorkingUnitsService workingUnitsService) {
		return args -> {
			userService.saveRole(new Role("ROLE_USER"));
			userService.saveRole(new Role("ROLE_ADMIN"));
			userService.saveRole(new Role("ROLE_ENJOYING_ADMIN"));
			//workingUnitsService.saveWorkingUnit(new WorkingUnits("test", "test", "test", null));
		
			
			userService.saveUser(new Users("AleksaEnjoying", "Aleksa", "Pancic", "aleksa.pantic@enjoying.rs",
								"1234", "+381641141063", null, true, new ArrayList<>()));
			
			userService.addRoleToUser("AleksaEnjoying", "ROLE_ENJOYING_ADMIN");
			//userService.addWorkingUnitToUser("AleksaEnjoying", "test");
		};
	}
}
