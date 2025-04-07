package com.DeskBooking.deskbooking.controller.request;

import lombok.Data;

@Data
public class ScheduleDataRequest {
    private String dateFrom;
    private String dateTo;
    private String username;
    private String deskName;
    private Long id;
}
