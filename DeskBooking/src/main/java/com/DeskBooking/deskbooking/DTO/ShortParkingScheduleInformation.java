package com.DeskBooking.deskbooking.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShortParkingScheduleInformation {
    public Long id;
    public Date dateFrom;
    public Date dateTo;
    public String firstName;
    public String lastName;
    public String parkingName;
    public String workingUnitName;
    public Boolean status;
    public Boolean parkingAvailability;
}
