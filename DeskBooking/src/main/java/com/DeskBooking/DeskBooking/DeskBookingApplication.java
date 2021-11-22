package com.DeskBooking.DeskBooking;



import java.util.Arrays;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.DeskBooking.DeskBooking.Models.*;
import com.DeskBooking.DeskBooking.Repositories.RoleRepository;
import com.DeskBooking.DeskBooking.Repositories.WorkingUnitsRepository;
import com.DeskBooking.DeskBooking.Services.CustomUserDetailService;
import com.DeskBooking.DeskBooking.Services.DesksServiceImpl;
import com.DeskBooking.DeskBooking.Services.OfficesServiceImpl;
import com.DeskBooking.DeskBooking.Services.WorkingUnitsServiceImpl;

@SpringBootApplication
public class DeskBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeskBookingApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	//popunjava bazu. 
	//  OBAVEZNO ZAKOMENTARISI OPET KAD POPUNIS BAZU, STVARAJU SE DUPLIKATI
	/*@Bean
	CommandLineRunner run(WorkingUnitsRepository workingUnitsRepository, CustomUserDetailService userService,
			WorkingUnitsServiceImpl workingUnitsService,OfficesServiceImpl officeService, DesksServiceImpl deskService,
			RoleRepository roleRepository) {
		return args -> {
			userService.saveRole(new Roles("ROLE_USER"));
			userService.saveRole(new Roles("ROLE_ADMIN"));
			userService.saveRole(new Roles("ROLE_ENJOYING_ADMIN"));
			
			WorkingUnits workingUnit1 =  workingUnitsService.saveWorkingUnit(new WorkingUnits("Beograd Office", City.BEOGRAD,
					"Milutina Milankovića 11g, GTC GreenHeart N1, Beograd", "+381 11 40 30 150", null));
			WorkingUnits workingUnit2 =workingUnitsService.saveWorkingUnit(new WorkingUnits("Nis Office", City.NIS,
					"Nikole Pašića 28, 4th floor, Niš", "+381 18 300 156", null));
			WorkingUnits workingUnit3 = workingUnitsService.saveWorkingUnit(new WorkingUnits("Kragujevac Office", City.KRAGUJEVAC,
					"Kralja Petra Prvog 26, 2nd floor, Kragujevac", "+381 11 40 30 150", null));
			
			Offices office1 = officeService.saveOffice(new Offices("office 1", workingUnit1 , true));
			Offices office2 = officeService.saveOffice(new Offices("office 2", workingUnit1 , true));
			Offices office3 = officeService.saveOffice(new Offices("office 3", workingUnit1 , true));
			Offices office4 = officeService.saveOffice(new Offices("office 4", workingUnit1 , true));
			Offices office5 = officeService.saveOffice(new Offices("office 1", workingUnit2 , true));
			Offices office6 = officeService.saveOffice(new Offices("office 2", workingUnit2 , true));
			Offices office7 = officeService.saveOffice(new Offices("office 3", workingUnit2 , true));
			Offices office8 = officeService.saveOffice(new Offices("office 1", workingUnit3 , true));
			Offices office9 = officeService.saveOffice(new Offices("office 2", workingUnit3 , true));
			Offices office10 = officeService.saveOffice(new Offices("office 3", workingUnit3 , true));
			Offices office11 = officeService.saveOffice(new Offices("office 4", workingUnit3 , true));
			
			Desks desks11 = deskService.saveDesk(new Desks("desk 1", true, office1));
			Desks desks12 = deskService.saveDesk(new Desks("desk 2", true, office1));
			Desks desks13 = deskService.saveDesk(new Desks("desk 3", true, office1));
			Desks desks14 = deskService.saveDesk(new Desks("desk 4", true, office1));
			Desks desks15 = deskService.saveDesk(new Desks("desk 5", true, office1));
			Desks desks16 = deskService.saveDesk(new Desks("desk 6", true, office1));
			Desks desks17 = deskService.saveDesk(new Desks("desk 7", true, office1));
			Desks desks18 = deskService.saveDesk(new Desks("desk 8", true, office1));
			
			Desks desks21 = deskService.saveDesk(new Desks("desk 1", true, office2));
			Desks desks22 = deskService.saveDesk(new Desks("desk 2", true, office2));
			Desks desks23 = deskService.saveDesk(new Desks("desk 3", true, office2));
			
			Desks desks31 = deskService.saveDesk(new Desks("desk 1", true, office3));
			Desks desks32 = deskService.saveDesk(new Desks("desk 2", true, office3));
			Desks desks33 = deskService.saveDesk(new Desks("desk 3", true, office3));
			Desks desks34 = deskService.saveDesk(new Desks("desk 4", true, office3));
			
			Desks desks41 = deskService.saveDesk(new Desks("desk 1", true, office4));
			
			Desks desks51 = deskService.saveDesk(new Desks("desk 1", true, office5));
			Desks desks52 = deskService.saveDesk(new Desks("desk 1", true, office5));
			
			Desks desks61 = deskService.saveDesk(new Desks("desk 2", true, office6));
			Desks desks62 = deskService.saveDesk(new Desks("desk 3", true, office7));
			
			Desks desks71 = deskService.saveDesk(new Desks("desk 4", true, office10));
			
			Date date = new Date();
			
			userService.saveUser(new Users("pera", "Pera", "Peric", "pera@enjoying.rs", "pera", "5664565645", date, true,
				  workingUnitsRepository.findByUnitName("Beograd Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			
		};
	}*/
}
