package com.DeskBooking.DeskBooking.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String password;
	private String confirmPassword;
	private String telephone;
	private String workingUnitName;
}
