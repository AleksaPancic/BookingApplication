package com.DeskBooking.DeskBooking.controller.response;

import com.DeskBooking.DeskBooking.model.Offices;
import lombok.Data;

@Data
public class DeskResponse {
    private String name;
    private Boolean available;
    private Offices office;
}
