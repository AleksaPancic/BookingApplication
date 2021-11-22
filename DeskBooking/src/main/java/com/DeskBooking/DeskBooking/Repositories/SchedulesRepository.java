package com.DeskBooking.DeskBooking.Repositories;

import com.DeskBooking.DeskBooking.DTO.ShortScheduleInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DeskBooking.DeskBooking.Models.Schedules;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface SchedulesRepository extends JpaRepository<Schedules, Long>  {

    @Query("select new com.DeskBooking.DeskBooking.DTO.ShortScheduleInformation(s.id, s.dateFrom, s.dateTo, s.user.firstName, s.user.lastName, s.desk.name, " +
            "s.desk.office.name, s.desk.office.workingUnit.unitName, " +
            "s.status, s.desk.available, s.desk.office.available) " +
            "from Schedules s where s.user.username = :username")
    List<ShortScheduleInformation> getAllSchedulesShortFromUser(String username);

    @Query("select new com.DeskBooking.DeskBooking.DTO.ShortScheduleInformation(s.id, s.dateFrom, s.dateTo, s.user.firstName, s.user.lastName, s.desk.name, " +
            "s.desk.office.name, s.desk.office.workingUnit.unitName, " +
            "s.status, s.desk.available, s.desk.office.available) " +
            "from Schedules s where s.user.username = :username and " +
            "((s.dateFrom between :dateFrom and :dateTo) or " +
            "(s.dateTo between :dateFrom and :dateTo))")
    List<ShortScheduleInformation> getFromToSchedulesFromUser(String username, Date dateFrom, Date dateTo);

    @Modifying
    @Query("update Schedules s set s.status = false where s.id = :id")
    void disableSchedule(Long id);
}
