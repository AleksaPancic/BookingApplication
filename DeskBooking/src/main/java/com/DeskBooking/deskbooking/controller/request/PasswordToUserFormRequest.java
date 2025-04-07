package com.DeskBooking.deskbooking.controller.request;

import lombok.Data;

@Data
public class PasswordToUserFormRequest {
    private String username;
    private String password;
}
