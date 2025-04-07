package com.DeskBooking.deskbooking.DTO;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShortScheduleInformation {
    public Long id;
    public Date dateFrom;
    public Date dateTo;
    public String firstName;
    public String lastName;
    public String deskName;
    public String officeName;
    public String workingUnitName;
    public Boolean status;
    public Boolean deskAvailability;
    public Boolean officeAvailability;
}
