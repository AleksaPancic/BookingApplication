package com.DeskBooking.DeskBooking.Services;

import java.util.List;


import com.DeskBooking.DeskBooking.Models.Roles;
import com.DeskBooking.DeskBooking.Models.Users;

public interface UsersService {
	Users saveUser(Users user);
	Roles saveRole(Roles role);
	public void addUsername(String usernameact, String username);
	public void addPassword(String username, String password);
	public void changeAll(String usernameact, String  firstName
			, String lastName, String telephone, String email);
	void addWorkingUnitToUser(String username, String workingunitname);
	void addRoleToUser(String username, String roleName);
	void addTelephone(String username, String telephone);
	void addEmail(String username, String email);
	void addFirstName(String username, String firstname);
	void addLastName(String username, String lastname);
	void ChangeActivityForUser(String username, Boolean active);
	String getFirstName(String username);
	String getLastName(String username);
	String getEmail(String username);
	String getWorkingUnit(String username);
	String getTelephone(String username);
	Users getUser(String username);
	List<Users> getUsers(Integer pageNo, Integer pageSize);
}
