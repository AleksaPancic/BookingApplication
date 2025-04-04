package com.DeskBooking.DeskBooking;

import com.DeskBooking.DeskBooking.model.*;
import com.DeskBooking.DeskBooking.repository.RoleRepository;
import com.DeskBooking.DeskBooking.repository.WorkingUnitsRepository;
import com.DeskBooking.DeskBooking.service.impl.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class DeskBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeskBookingApplication.class, args);
	}

	@Bean
	CommandLineRunner run(WorkingUnitsRepository workingUnitsRepository, CustomUserDetailService userService,
						  WorkingUnitsServiceImpl workingUnitsService, OfficesServiceImpl officeService, DesksServiceImpl deskService,
						  RoleRepository roleRepository, SchedulesServiceImpl schedulesService, ParkingServiceImpl parkingService) {
		return args -> {
			userService.saveRole(new Role("ROLE_USER"));
			userService.saveRole(new Role("ROLE_ADMIN"));
			userService.saveRole(new Role("ROLE_ENJOYING_ADMIN"));

			WorkingUnit workingUnit1 =  workingUnitsService.saveWorkingUnit(new WorkingUnit("Beograd Office", City.BEOGRAD,
					"Milutina Milankovića 11g, GTC GreenHeart N1, Beograd", "+381 11 40 30 150", "http://localhost:8080/images/WorkingUnitsMaps/BG_map.png", "http://localhost:8080/images/WorkingUnitsMaps/BG_parking_map_level_1.png,http://localhost:8080/images/WorkingUnitsMaps/BG_parking_map_level_3.png"));
			WorkingUnit workingUnit2 =workingUnitsService.saveWorkingUnit(new WorkingUnit("Nis Office", City.NIS,
					"Nikole Pašića 28, 4th floor, Niš", "+381 18 300 156", "http://localhost:8080/images/WorkingUnitsMaps/NI_map.png", null));
			WorkingUnit workingUnit3 = workingUnitsService.saveWorkingUnit(new WorkingUnit("Kragujevac Office", City.KRAGUJEVAC,
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

			Desk desk1 = deskService.saveDesk(new Desk("desk 1", true, office1));
			Desk desk2 = deskService.saveDesk(new Desk("desk 2", true, office1));
			Desk desk3 = deskService.saveDesk(new Desk("desk 3", true, office1));
			Desk desk4 = deskService.saveDesk(new Desk("desk 4", true, office1));
			Desk desk5 = deskService.saveDesk(new Desk("desk 5", true, office1));
			Desk desk6 = deskService.saveDesk(new Desk("desk 6", false, office1));
			Desk desk7 = deskService.saveDesk(new Desk("desk 7", false, office1));
			Desk desk8 = deskService.saveDesk(new Desk("desk 8", true, office1));

			Desk desk9 = deskService.saveDesk(new Desk("desk 9", true, office2));
			Desk desk10 = deskService.saveDesk(new Desk("desk 10", false, office2));
			Desk desk11 = deskService.saveDesk(new Desk("desk 11", true, office2));

			Desk desk12 = deskService.saveDesk(new Desk("desk 12", true, office3));
			Desk desk13 = deskService.saveDesk(new Desk("desk 13", true, office3));
			Desk desk14 = deskService.saveDesk(new Desk("desk 14", true, office3));
			Desk desk15 = deskService.saveDesk(new Desk("desk 15", false, office3));

			Desk desk16 = deskService.saveDesk(new Desk("desk 16", true, office4));

			Desk desk17 = deskService.saveDesk(new Desk("desk 17", true, office5));
			Desk desk18 = deskService.saveDesk(new Desk("desk 18", true, office5));

			Desk desk19 = deskService.saveDesk(new Desk("desk 19", true, office6));

			Desk desk20 = deskService.saveDesk(new Desk("desk 20", true, office7));

			Desk desk21 = deskService.saveDesk(new Desk("desk 21", true, office10));

			Parking parking1 = parkingService.saveParking(new Parking("L-1 PB-1", true, workingUnit1));
			Parking parking2 = parkingService.saveParking(new Parking("L-1 PB-2", true, workingUnit1));
			Parking parking3 = parkingService.saveParking(new Parking("L-1 PB-3", true, workingUnit1));
			Parking parking4 = parkingService.saveParking(new Parking("L-1 PB-4", true, workingUnit1));
			Parking parking5 = parkingService.saveParking(new Parking("L-3 PB-5", true, workingUnit1));
			Parking parking6 = parkingService.saveParking(new Parking("L-3 PB-6", true, workingUnit1));
			Parking parking7 = parkingService.saveParking(new Parking("L-3 PB-7", true, workingUnit1));


			Date date = new Date(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());

			User pera = userService.saveUser(new User("pera", "Pera", "Peric", "pera@enjoying.rs", "1234", "5664565645", date, true,
				  workingUnitsRepository.findByUnitName("Beograd Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			User mika = userService.saveUser(new User("mika", "Mika", "Mikic", "mika@enjoying.rs", "1234", "5664565645", date, true,
				  workingUnitsRepository.findByUnitName("Beograd Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("zika", "Zika", "Peric", "zika@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Kragujevac Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("ana", "Ana", "Peric", "ana@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("sanja", "Sanja", "Peric", "pera@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("bosko", "Bosko", "Mikic", "bosko@enjoying.rs", "1234", "5664565645", date, true,
				  workingUnitsRepository.findByUnitName("Beograd Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("neca", "Nemanja", "Peric", "neca@enjoying.rs", "1234", "5664565645", date, true,
						  workingUnitsRepository.findByUnitName("Kragujevac Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("bilja", "Biljana", "Peric", "ana@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("peca", "Petar", "Milic", "pera@enjoying.rs", "1234", "5664565645", date, true,
						  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("marko", "Marko", "Peric", "marko@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Beograd Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("aleksa", "Aca", "Peric", "aca@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Beograd Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("milos", "Milos", "Peric", "milos@enjoying.rs", "1234", "5664565645", date, true,
						  workingUnitsRepository.findByUnitName("Kragujevac Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("filip", "Filip", "Peric", "filip@enjoying.rs", "1234", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("nikola", "Nikola", "Peric", "nikola@enjoying.rs", "1234", "5664565645", date, true,
						  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("jelena", "Jelena", "Mikic", "jelena@enjoying.rs", "1234", "5664565645", date, true,
						  workingUnitsRepository.findByUnitName("Beograd Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("milica", "Milica", "Peric", "mica@enjoying.rs", "1234", "5664565645", date, true,
							  workingUnitsRepository.findByUnitName("Kragujevac Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("nesa", "Nesa", "Peric", "nesa@enjoying.rs", "1234", "5664565645", date, true,
							  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("dragan", "Dragan", "Milic", "dragan@enjoying.rs", "1234", "5664565645", date, true,
							  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_USER"))));
			userService.saveUser(new User("ADMIN", "ADMIN", "ADMIN", "admin@enjoying.rs", "ADMIN", "5664565645", date, true,
							  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_ADMIN"))));
			userService.saveUser(new User("SUPERADMINNIS", "SUPER", "ADMIN", "admin@enjoying.rs", "nBBQ9PtzoJK8ppta71", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_ENJOYING_ADMIN"))));
			userService.saveUser(new User("SUPERADMINBEOGRAD", "SUPER", "ADMIN", "admin@enjoying.rs", "nBBQ9PtzoJK8ppta72", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_ENJOYING_ADMIN"))));
			userService.saveUser(new User("SUPERADMINKRAGUJEVAC", "SUPER", "ADMIN", "admin@enjoying.rs", "nBBQ9PtzoJK8ppta73", "5664565645", date, true,
					  workingUnitsRepository.findByUnitName("Nis Office"), Arrays.asList(roleRepository.findByName("ROLE_ENJOYING_ADMIN"))));
			userService.saveUser(new User("super", "SUPER", "ADMIN", "admin@enjoying.rs", "1234", "5664565645", date, true,
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
	}
}
