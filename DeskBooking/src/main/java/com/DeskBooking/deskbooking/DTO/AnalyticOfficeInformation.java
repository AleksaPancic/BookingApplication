package com.DeskBooking.deskbooking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnalyticOfficeInformation {
	public Long workingUnitId;
	public Long numOfEnableOffices;
	public Long numOfDisableOffices;
}
