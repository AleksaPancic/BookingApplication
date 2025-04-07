package com.DeskBooking.deskbooking.repository;


import com.DeskBooking.deskbooking.DTO.ShortParkingScheduleInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DeskBooking.deskbooking.model.Parking;
import com.DeskBooking.deskbooking.model.ParkingSchedule;

import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ParkingSchedulesRepository extends JpaRepository<ParkingSchedule, Long>  {
		
    @Query("select new com.DeskBooking.deskbooking.DTO.ShortParkingScheduleInformation(s.id, s.dateFrom, s.dateTo, s.user.firstName, s.user.lastName, s.parking.name, " +
            "s.parking.workingUnit.unitName, " +
            "s.status, s.parking.available) " +
            "from ParkingSchedule s where s.user.username = :username and " +
            "s.status = true")
    List<ShortParkingScheduleInformation> getParkingAllSchedulesShortFromUser(String username);

    @Query("select new com.DeskBooking.deskbooking.DTO.ShortParkingScheduleInformation(s.id, s.dateFrom, s.dateTo, s.user.firstName, s.user.lastName, s.parking.name, " +
            "s.parking.workingUnit.unitName, " +
            "s.status, s.parking.available) " +
            "from ParkingSchedule s where s.user.username = :username and " +
            "((s.dateFrom between :dateFrom and :dateTo) or " +
            "(s.dateTo between :dateFrom and :dateTo)) and " +
            "s.status = true")
    List<ShortParkingScheduleInformation> getFromToParkingSchedulesFromUser(String username, Date dateFrom, Date dateTo);

	
    @Modifying
    @Query("update ParkingSchedule s set s.status = false where s.id = :id")
    void disableParkingSchedule(Long id);
    
    @Query("select s from ParkingSchedule s where (s.dateFrom between :dateFrom and :dateTo) and (s.dateTo between :dateFrom and :dateTo) and "
    		+ "s.parking.name = :parkingName and s.status = true")
    public List<ParkingSchedule> getAllParkingSchedules(Date dateFrom, Date dateTo, String parkingName);
    
    @Query("select s from ParkingSchedule s where (s.dateFrom < :dateTo and s.dateTo > :dateFrom) and "
    		+ "s.parking.name = :parkingName and s.status = true")
    public ParkingSchedule checkParkingSchedule(Date dateFrom, Date dateTo, String parkingName);

	Optional<ParkingSchedule> findById(Long id);
	List<ParkingSchedule> findByParking(Parking parking);
}
