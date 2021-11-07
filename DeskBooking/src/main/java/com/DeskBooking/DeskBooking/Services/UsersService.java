package com.DeskBooking.DeskBooking.Services;

import java.util.List;

import com.DeskBooking.DeskBooking.Models.Role;
import com.DeskBooking.DeskBooking.Models.Users;

public interface UsersService {
	Users saveUser(Users user);
	Role saveRole(Role role);
	void addWorkingUnitToUser(String username, String workingunitname);
	void addRoleToUser(String username, String roleName);
	void addTelephone(String username, String telephone);
	void addEmail(String username, String email);
	void addFirstName(String username, String firstname);
	void addLastName(String username, String lastname);
	void ChangeActivityForUser(String username, Boolean active);
	Users getUser(String username);
	List<Users> getUsers();
}
