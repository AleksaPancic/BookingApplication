package com.DeskBooking.deskbooking.controller.request;

import lombok.Data;

@Data
public class RoleToUserFormRequest {
    private String username;
    private String roleName;
}
