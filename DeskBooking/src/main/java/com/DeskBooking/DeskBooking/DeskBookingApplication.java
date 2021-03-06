package com.DeskBooking.DeskBooking;

import java.sql.Timestamp;
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
import com.DeskBooking.DeskBooking.Services.ParkingServiceImpl;
import com.DeskBooking.DeskBooking.Services.SchedulesServiceImpl;
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
			RoleRepository roleRepository, SchedulesServiceImpl schedulesService, ParkingServiceImpl parkingService) {
		return args -> {
			userService.saveRole(new Roles("ROLE_USER"));
			userService.saveRole(new Roles("ROLE_ADMIN"));
			userService.saveRole(new Roles("ROLE_ENJOYING_ADMIN"));
			
			WorkingUnits workingUnit1 =  workingUnitsService.saveWorkingUnit(new WorkingUnits("Beograd Office", City.BEOGRAD,
					"Milutina Milankovi??a 11g, GTC GreenHeart N1, Beograd", "+381 11 40 30 150", "http://localhost:8080/images/WorkingUnitsMaps/BG_map.png", "http://localhost:8080/images/WorkingUnitsMaps/BG_parking_map_level_1.png,http://localhost:8080/images/WorkingUnitsMaps/BG_parking_map_level_3.png"));
			WorkingUnits workingUnit2 =workingUnitsService.saveWorkingUnit(new WorkingUnits("Nis Office", City.NIS,
					"Nikole Pa??i??a 28, 4th floor, Ni??", "+381 18 300 156", "http://localhost:8080/images/WorkingUnitsMaps/NI_map.png", null));
			WorkingUnits workingUnit3 = workingUnitsService.saveWorkingUnit(new WorkingUnits("Kragujevac Office", City.KRAGUJEVAC,
					"Kralja Petra Prvog 26, 2nd floor, Kragujevac", "+381 11 40 30 150", "http://localhost:8080/images/WorkingUnitsMaps/KG_map.png", null));
			
			Offices office1 = officeService.saveOffice(new Offices("office 1", workingUnit1 , true));
			Offices office2 = officeService.saveOffice(new Offices("office 2", workingUnit1 , true));
			Offices office3 = officeService.saveOffice(new Offices("office 3", workingUnit1 , true));
			Offices office4 = officeService.saveOffice(new Offices("office 4", workingUnit1 , true));
			Offices office5 = officeService.saveOffice(new Offices("office 5", workingUnit2 , true));
			Offices office6 = officeService.saveOffice(new Offices("office 6", workingUnit2 , true));
			Offices office7 = officeService.saveOffice(new Offices("office 7", workingUnit2 , false));
			Offices office8 = officeService.saveOffice(new Offices("office 8", workingUnit3 , true));
			Offices office9 = officeService.saveOffice(new Offices("office 9", workingUnit3 , true));
			Offices office10 = officeService.saveOffice(new Offices("office 10", workingUnit3 , true));
			Offices office11 = officeService.saveOffice(new Offices("office 11", workingUnit3 , false));
			
			Desks desk1 = deskService.saveDesk(new Desks("desk 1", true, office1));
			Desks desk2 = deskService.saveDesk(new Desks("desk 2", true, office1));
			Desks desk3 = deskService.saveDesk(new Desks("desk 3", true, office1));
			Desks desk4 = deskService.saveDesk(new Desks("desk 4", true, office1));
			Desks desk5 = deskService.saveDesk(new Desks("desk 5", true, office1));
			Desks desk6 = deskService.saveDesk(new Desks("desk 6", false, office1));
			Desks desk7 = deskService.saveDesk(new Desks("desk 7", false, office1));
			Desks desk8 = deskService.saveDesk(new Desks("desk 8", true, office1));
			
			Desks desk9 = deskService.saveDesk(new Desks("desk 9", true, office2));
			Desks desk10 = deskService.saveDesk(new Desks("desk 10", false, office2));
			Desks desk11 = deskService.saveDesk(new Desks("desk 11", true, office2));
			
			Desks desk12 = deskService.saveDesk(new Desks("desk 12", true, office3));
			Desks desk13 = deskService.saveDesk(new Desks("desk 13", true, office3));
			Desks desk14 = deskService.saveDesk(new Desks("desk 14", true, office3));
			Desks desk15 = deskService.saveDesk(new Desks("desk 15", false, office3));
			
			Desks desk16 = deskService.saveDesk(new Desks("desk 16", true, office4));
			
			Desks desk17 = deskService.saveDesk(new Desks("desk 17", true, office5));
			Desks desk18 = deskService.saveDesk(new Desks("desk 18", true, office5));
			
			Desks desk19 = deskService.saveDesk(new Desks("desk 19", true, office6));
			Desks desk20 = deskService.saveDesk(new Desks("desk 20", true, office7));
			
			Desks desk21 = deskService.saveDesk(new Desks("desk 21", true, office10));
			
			Parking parking1 = parkingService.saveParking(new Parking("L-1 PB-1", true, workingUnit1));	
			Parking parking2 = parkingService.saveParking(new Parking("L-1 PB-2", true, workingUnit1));
			Parking parking3 = parkingService.saveParking(new Parking("L-1 PB-3", true, workingUnit1));
			Parking parking4 = parkingService.saveParking(new Parking("L-1 PB-4", true, workingUnit1));
			Parking parking5 = parkingService.saveParking(new Parking("L-3 PB-5", true, workingUnit1));
			Parking parking6 = parkingService.saveParking(new Parking("L-3 PB-6", true, workingUnit1));
			Parking parking7 = parkingService.saveParking(new Parking("L-3 PB-7", true, workingUnit1));
			
			
			Date date = new Date();
			
			Users pera = userService.saveUser(new Users("pera", "Pera", "Peric", "pera@enjoying.rs", "1234", "5664565645", date, true,
				  workingUnitsRepository.findByUnitName("Beograd Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			Users mika = userService.saveUser(new Users("mika", "Mika", "Mikic", "mika@enjoying.rs", "1234", "5664565645", date, true,
				  workingUnitsRepository.findByUnitName("Beograd Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("zika", "Zika", "Peric", "zika@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Kragujevac Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("ana", "Ana", "Peric", "ana@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("sanja", "Sanja", "Peric", "pera@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("bosko", "Bosko", "Mikic", "bosko@enjoying.rs", "1234", "5664565645", date, true,
				  workingUnitsRepository.findByUnitName("Beograd Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("neca", "Nemanja", "Peric", "neca@enjoying.rs", "1234", "5664565645", date, true,
						  workingUnitsRepository.findByUnitName("Kragujevac Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("bilja", "Biljana", "Peric", "ana@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("peca", "Petar", "Milic", "pera@enjoying.rs", "1234", "5664565645", date, true,
						  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("marko", "Marko", "Peric", "marko@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Beograd Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("aleksa", "Aca", "Peric", "aca@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Beograd Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("milos", "Milos", "Peric", "milos@enjoying.rs", "1234", "5664565645", date, true,
						  workingUnitsRepository.findByUnitName("Kragujevac Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("filip", "Filip", "Peric", "filip@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("nikola", "Nikola", "Peric", "nikola@enjoying.rs", "1234", "5664565645", date, true,
						  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("jelena", "Jelena", "Mikic", "jelena@enjoying.rs", "1234", "5664565645", date, true,
						  workingUnitsRepository.findByUnitName("Beograd Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("milica", "Milica", "Peric", "mica@enjoying.rs", "1234", "5664565645", date, true,
							  workingUnitsRepository.findByUnitName("Kragujevac Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("nesa", "Nesa", "Peric", "nesa@enjoying.rs", "1234", "5664565645", date, true,
							  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("dragan", "Dragan", "Milic", "dragan@enjoying.rs", "1234", "5664565645", date, true,
							  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new Users("ADMIN", "ADMIN", "ADMIN", "admin@enjoying.rs", "ADMIN", "5664565645", date, true,
							  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_ADMIN"))));
			userService.saveUser(new Users("SUPERADMINNIS", "SUPER", "ADMIN", "admin@enjoying.rs", "nBBQ9PtzoJK8ppta71", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_ENJOYING_ADMIN"))));
			userService.saveUser(new Users("SUPERADMINBEOGRAD", "SUPER", "ADMIN", "admin@enjoying.rs", "nBBQ9PtzoJK8ppta72", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_ENJOYING_ADMIN"))));
			userService.saveUser(new Users("SUPERADMINKRAGUJEVAC", "SUPER", "ADMIN", "admin@enjoying.rs", "nBBQ9PtzoJK8ppta73", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_ENJOYING_ADMIN"))));
			userService.saveUser(new Users("super", "SUPER", "ADMIN", "admin@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_ENJOYING_ADMIN"))));
			
			schedulesService.saveSchedules(new Schedules(true, Timestamp.valueOf("2021-12-06 09:00:00"),
								Timestamp.valueOf("2021-12-06 17:00:00"), pera, desk1));
			schedulesService.saveSchedules(new Schedules(true, Timestamp.valueOf("2021-12-06 09:00:00"),
					Timestamp.valueOf("2021-12-06 17:00:00"), mika, desk2));
			schedulesService.saveSchedules(new Schedules(true, Timestamp.valueOf("2021-12-07 14:00:00"),
					Timestamp.valueOf("2021-12-07 17:00:00"), pera, desk4));
			schedulesService.saveSchedules(new Schedules(true, Timestamp.valueOf("2021-12-07 08:00:00"),
					Timestamp.valueOf("2021-12-07 14:00:00"), mika, desk4));
			schedulesService.saveSchedules(new Schedules(true, Timestamp.valueOf("2021-12-08 09:00:00"),
					Timestamp.valueOf("2021-12-06 12:00:00"), pera, desk5));
			schedulesService.saveSchedules(new Schedules(true, Timestamp.valueOf("2021-12-08 13:00:00"),
					Timestamp.valueOf("2021-12-08 15:30:00"), mika, desk4));
		};
	}*/
}
