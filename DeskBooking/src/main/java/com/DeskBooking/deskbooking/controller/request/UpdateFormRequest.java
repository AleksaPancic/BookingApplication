package com.DeskBooking.deskbooking.controller.request;

import lombok.Data;

@Data
public class UpdateFormRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String workingUnit;
    private String telephone;
}
