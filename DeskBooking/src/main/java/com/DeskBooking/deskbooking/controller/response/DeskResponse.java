package com.DeskBooking.deskbooking.controller.response;

import com.DeskBooking.deskbooking.model.Offices;
import lombok.Data;

@Data
public class DeskResponse {
    private String name;
    private Boolean available;
    private Offices office;
}
