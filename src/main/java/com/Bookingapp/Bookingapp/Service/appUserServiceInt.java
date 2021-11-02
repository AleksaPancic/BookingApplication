package com.Bookingapp.Bookingapp.Service;

import java.util.List;

import com.Bookingapp.Bookingapp.Models.Users;

public interface appUserServiceInt {
	List<Users> getAllUsers();
	long countUsers();
}
