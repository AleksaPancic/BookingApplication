package com.DeskBooking.deskbooking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnalyticParkingScheduleInformation {
		public Long workingUnitId;
		public Long numOfTotalSchedules;
	 	public Long numOfSchedules;
	    public Long numOfCanceledSchedules;
	    public Float avgDailyReservation;
	    public Long numOfEnableParkings;
	    public Long numOfDisableParkings;
	    
	    public AnalyticParkingScheduleInformation(Long workingUnitId, Long numOfTotalSchedules, Long numOfSchedules, Long numOfCanceledSchedules) {
	    	this.workingUnitId = workingUnitId;
	    	this.numOfTotalSchedules = numOfTotalSchedules;
	    	this.numOfSchedules = numOfSchedules;
	    	this.numOfCanceledSchedules = numOfCanceledSchedules;
	    }
	    
	    public AnalyticParkingScheduleInformation(Long numOfEnableParkings, Long numOfDisableParkings) {
	    	this.numOfEnableParkings = numOfEnableParkings;
	    	this.numOfDisableParkings = numOfDisableParkings;
	    }
}
