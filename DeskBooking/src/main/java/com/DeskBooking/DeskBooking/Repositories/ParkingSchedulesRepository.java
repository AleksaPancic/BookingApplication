package com.DeskBooking.DeskBooking.Repositories;


import com.DeskBooking.DeskBooking.DTO.ShortParkingScheduleInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DeskBooking.DeskBooking.Models.Parking;
import com.DeskBooking.DeskBooking.Models.ParkingSchedules;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ParkingSchedulesRepository extends JpaRepository<ParkingSchedules, Long>  {
		
    @Query("select new com.DeskBooking.DeskBooking.DTO.ShortParkingScheduleInformation(s.id, s.dateFrom, s.dateTo, s.user.firstName, s.user.lastName, s.parking.name, " +
            "s.parking.workingUnit.unitName, " +
            "s.status, s.parking.available) " +
            "from ParkingSchedules s where s.user.username = :username and " +
            "s.status = true")
    List<ShortParkingScheduleInformation> getParkingAllSchedulesShortFromUser(String username);

    @Query("select new com.DeskBooking.DeskBooking.DTO.ShortParkingScheduleInformation(s.id, s.dateFrom, s.dateTo, s.user.firstName, s.user.lastName, s.parking.name, " +
            "s.parking.workingUnit.unitName, " +
            "s.status, s.parking.available) " +
            "from ParkingSchedules s where s.user.username = :username and " +
            "((s.dateFrom between :dateFrom and :dateTo) or " +
            "(s.dateTo between :dateFrom and :dateTo)) and " +
            "s.status = true")
    List<ShortParkingScheduleInformation> getFromToParkingSchedulesFromUser(String username, Date dateFrom, Date dateTo);

	
    @Modifying
    @Query("update ParkingSchedules s set s.status = false where s.id = :id")
    void disableParkingSchedule(Long id);
    
    @Query("select s from ParkingSchedules s where (s.dateFrom between :dateFrom and :dateTo) and (s.dateTo between :dateFrom and :dateTo) and "
    		+ "s.parking.name = :parkingName and s.status = true")
    public List<ParkingSchedules> getAllParkingSchedules(Date dateFrom, Date dateTo, String parkingName);
    
    @Query("select s from ParkingSchedules s where (s.dateFrom < :dateTo and s.dateTo > :dateFrom) and "
    		+ "s.parking.name = :parkingName and s.status = true")
    public ParkingSchedules checkParkingSchedule(Date dateFrom, Date dateTo, String parkingName);

	Optional<ParkingSchedules> findById(Long id);
	List<ParkingSchedules> findByParking(Parking parking);
}
