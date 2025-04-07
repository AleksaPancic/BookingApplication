package com.DeskBooking.deskbooking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TopMostUsedOffices {
	public Long workingUnitId;
	public Long officeId;
	public String officeName;
	public Long numOfUsed;
}
