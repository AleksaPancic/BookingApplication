package com.DeskBooking.DeskBooking.service;

import java.util.List;


import com.DeskBooking.DeskBooking.model.Roles;
import com.DeskBooking.DeskBooking.model.Users;

public interface UsersService {
	Users saveUser(Users user);
	Roles saveRole(Roles role);
	public void addUsername(String usernameact, String username);
	public void addPassword(String username, String password);
	public String resetPassword(String username);
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
	void deleteUser(Users user);
	void disableUser(Users user);
	void changeUserActivity(Users user);
	void addAdminRole(Users user);
	void removeAdminRole(Users user);
	List<Users> getUsersSearch(Integer pageNo, Integer pageSize, String name);
	int getUsersSearchCount(String name);
	void removeSuperAdminRole(Users user);
	void removeUserRole(Users user);
}
