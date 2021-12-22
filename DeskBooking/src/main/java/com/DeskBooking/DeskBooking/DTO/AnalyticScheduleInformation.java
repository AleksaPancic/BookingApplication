package com.DeskBooking.DeskBooking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnalyticScheduleInformation {
    public Long workingUnitId;
    public Long numOfTotalSchedules;
    public Long numOfSchedules;
    public Long numOfCanceledSchedules;
    public Float avgDailyReservation;
    
    public AnalyticScheduleInformation(Long workingUnitId, Long numOfTotalSchedules, Long numOfSchedules, Long numOfCanceledSchedules) {
    	this.workingUnitId = workingUnitId;
    	this.numOfTotalSchedules = numOfTotalSchedules;
    	this.numOfSchedules = numOfSchedules;
    	this.numOfCanceledSchedules = numOfCanceledSchedules;
    }
}
