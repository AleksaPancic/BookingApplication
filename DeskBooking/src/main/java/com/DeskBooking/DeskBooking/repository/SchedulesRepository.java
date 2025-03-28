package com.DeskBooking.DeskBooking.repository;


import com.DeskBooking.DeskBooking.DTO.AnalyticDeskInformation;
import com.DeskBooking.DeskBooking.DTO.AnalyticOfficeInformation;
import com.DeskBooking.DeskBooking.DTO.AnalyticParkingInformation;
import com.DeskBooking.DeskBooking.DTO.AnalyticParkingScheduleInformation;
import com.DeskBooking.DeskBooking.DTO.AnalyticScheduleInformation;
import com.DeskBooking.DeskBooking.DTO.ShortScheduleInformation;
import com.DeskBooking.DeskBooking.DTO.TopMostUsedDesks;
import com.DeskBooking.DeskBooking.DTO.TopMostUsedOffices;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DeskBooking.DeskBooking.model.Desks;
import com.DeskBooking.DeskBooking.model.Schedules;

import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface SchedulesRepository extends JpaRepository<Schedules, Long>  {
	
	//For HomeController
	
    @Query("select new com.DeskBooking.DeskBooking.DTO.ShortScheduleInformation(s.id, s.dateFrom, s.dateTo, s.user.firstName, s.user.lastName, s.desk.name, " +
            "s.desk.office.name, s.desk.office.workingUnit.unitName, " +
            "s.status, s.desk.available, s.desk.office.available) " +
            "from Schedules s where s.user.username = :username and " +
            "s.status = true")
    List<ShortScheduleInformation> getAllSchedulesShortFromUser(String username);

    @Query("select new com.DeskBooking.DeskBooking.DTO.ShortScheduleInformation(s.id, s.dateFrom, s.dateTo, s.user.firstName, s.user.lastName, s.desk.name, " +
            "s.desk.office.name, s.desk.office.workingUnit.unitName, " +
            "s.status, s.desk.available, s.desk.office.available) " +
            "from Schedules s where s.user.username = :username and " +
            "((s.dateFrom between :dateFrom and :dateTo) or " +
            "(s.dateTo between :dateFrom and :dateTo)) and " +
            "s.status = true")
    List<ShortScheduleInformation> getFromToSchedulesFromUser(String username, Date dateFrom, Date dateTo);

    @Modifying
    @Query("update Schedules s set s.status = false where s.id = :id")
    void disableSchedule(Long id);
    
    //For BookingController
    
    @Query("select s from Schedules s where (s.dateFrom between :dateFrom and :dateTo) and (s.dateTo between :dateFrom and :dateTo) and "
    		+ "s.desk.name = :deskName and s.status = true")
    public List<Schedules> getAllSchedules(Date dateFrom, Date dateTo, String deskName);
    
    @Query("select s from Schedules s where (s.dateFrom < :dateTo and s.dateTo > :dateFrom) and "
    		+ "s.desk.name = :deskName and s.status = true")
    public Schedules checkSchedule(Date dateFrom, Date dateTo, String deskName);
 
    //For AdminController - Anaytics Information
    
    @Query("select new com.DeskBooking.DeskBooking.DTO.AnalyticScheduleInformation("
    		+ "(case when s.desk.office.workingUnit.id = null then :id else s.desk.office.workingUnit.id end),"
    		+ " count(s.id),"
    		+ "sum(case when s.status = true then 1 else 0 end), sum(case when s.status = false then 1 else 0 end))"
    		+ "from Schedules s"
    		+ " where s.dateFrom >= :dateFrom and s.dateTo <= :dateTo and s.desk.office.workingUnit.id = :id")
    public AnalyticScheduleInformation getAnayliticSchedules(Date dateFrom, Date dateTo, Long id);
    
    @Query("select count(*) from Schedules s where s.status = true and"
    		+ " s.dateFrom >= :dateFrom and s.dateTo <= :dateTo and s.desk.office.workingUnit.id = :id")
    public Float getAvgSchedules(Date dateFrom, Date dateTo, Long id);
    
    @Query("select new com.DeskBooking.DeskBooking.DTO.AnalyticOfficeInformation("
    		+ "(case when o.workingUnit.id = null then :id else o.workingUnit.id end),"
    		+ "sum(case when o.available = true then 1 else 0 end), sum(case when o.available = false then 1 else 0 end))"
    		+ " from Offices o"
    		+ " where o.workingUnit.id = :id")
    public AnalyticOfficeInformation getAnalyticOffices(Long id);
    
    @Query("select new com.DeskBooking.DeskBooking.DTO.AnalyticDeskInformation("
    		+ "(case when d.office.workingUnit.id = null then :id else d.office.workingUnit.id end),"
    		+ "sum(case when d.available = true then 1 else 0 end), sum(case when d.available = false then 1 else 0 end))"
    		+ " from Desks d"
    		+ " where d.office.workingUnit.id = :id")
    public AnalyticDeskInformation getAnalyticDesks(Long id);
    
    @Query("select new com.DeskBooking.DeskBooking.DTO.TopMostUsedOffices("
    		+ "(case when s.desk.office.workingUnit.id = null then :id else s.desk.office.workingUnit.id end),"
    		+ "s.desk.office.id, s.desk.office.name, count(s.desk.office.id)) "
    		+ "from Schedules s "
    		+ "where s.desk.office.workingUnit.id = :id and s.status = true and "
    		+ "s.dateFrom >= :dateFrom and s.dateTo <= :dateTo "
    		+ "group by s.desk.office.id "
    		+ "order by count(s.desk.office.id) desc")
    public List<TopMostUsedOffices> getTopUsedOffices(Date dateFrom, Date dateTo, Long id, Pageable paging);
    
    @Query("select new com.DeskBooking.DeskBooking.DTO.TopMostUsedDesks("
    		+ "(case when s.desk.office.workingUnit.id = null then :id else s.desk.office.workingUnit.id end), "
    		+ "s.desk.office.id, s.desk.id, s.desk.name, count(s.desk.id)) "
    		+ "from Schedules s "
    		+ "where s.desk.office.workingUnit.id = :id and s.status = true and "
    		+ "s.dateFrom >= :dateFrom and s.dateTo <= :dateTo "
    		+ "group by s.desk.id "
    		+ "order by count(s.desk.id) desc")
    public List<TopMostUsedDesks> getTopUsedDesks(Date dateFrom, Date dateTo, Long id, Pageable paging);
    
    //Anaylitic information for Parking
    
    @Query("select new com.DeskBooking.DeskBooking.DTO.AnalyticParkingScheduleInformation("
    		+ "(case when s.parking.workingUnit.id = null then :id else s.parking.workingUnit.id end), "
    		+ "count(s.id),"
    		+ "sum(case when s.status = true then 1 else 0 end), sum(case when s.status = false then 1 else 0 end)) "
    		+ "from ParkingSchedules s "
    		+ "where s.dateFrom >= :dateFrom and s.dateTo <= :dateTo and s.parking.workingUnit.id = :id")
    public AnalyticParkingScheduleInformation getAnayliticParkingSchedules(Date dateFrom, Date dateTo, Long id);
    
    @Query("select count(*) from ParkingSchedules s where s.status = true and "
    		+ "s.dateFrom >= :dateFrom and s.dateTo <= :dateTo and s.parking.workingUnit.id = :id")
    public Float getAvgParkingSchedules(Date dateFrom, Date dateTo, Long id);

    @Query("select new com.DeskBooking.DeskBooking.DTO.AnalyticParkingInformation("
    		+ "sum(case when p.available = true then 1 else 0 end), sum(case when p.available = false then 1 else 0 end)) "
    		+ "from Parking p "
    		+ "where p.workingUnit.id = :id")
    public AnalyticParkingInformation getAnalyticParking(Long id);
    
	Optional<Schedules> findById(Long id);
	List<Schedules> findByDesk(Desks desk);

}
