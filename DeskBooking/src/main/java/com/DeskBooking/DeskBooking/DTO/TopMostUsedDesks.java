package com.DeskBooking.DeskBooking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TopMostUsedDesks {
	public Long workingUnitId;
	public Long officeId;
	public Long deskId;
	public String deskName;
	public Long numOfUsed;
}
