package com.DeskBooking.DeskBooking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnalyticDeskInformation {
	public Long workingUnitId;
	public Long numOfEnableDesks;
	public Long numOfDisableDesks;
}
