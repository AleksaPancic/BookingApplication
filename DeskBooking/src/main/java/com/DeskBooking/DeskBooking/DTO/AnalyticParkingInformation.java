package com.DeskBooking.DeskBooking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnalyticParkingInformation {
	  public Long numOfEnableParkings;
	    public Long numOfDisableParkings;
}
