package com.Bookingapp.Bookingapp.Models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
//when the client sends a request we want to capture few things
public class RegistrationRequest {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	public RegistrationRequest(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

}
