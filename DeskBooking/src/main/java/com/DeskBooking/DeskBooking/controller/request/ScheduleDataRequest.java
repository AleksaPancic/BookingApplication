package com.DeskBooking.DeskBooking.controller.request;

import lombok.Data;

@Data
public class ScheduleDataRequest {
    private String dateFrom;
    private String dateTo;
    private String username;
    private String deskName;
    private Long id;
}
