package com.DeskBooking.DeskBooking.controller.request;

import lombok.Data;

@Data
public class ScheduleParkingDataRequest {
    private String dateFrom;
    private String dateTo;
    private String username;
    private String parkingName;
    private Long id;
}
