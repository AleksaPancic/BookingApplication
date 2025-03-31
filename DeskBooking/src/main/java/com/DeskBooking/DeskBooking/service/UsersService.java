package com.DeskBooking.DeskBooking.service;

import java.util.List;


import com.DeskBooking.DeskBooking.model.Roles;
import com.DeskBooking.DeskBooking.model.User;

public interface UsersService {
	User saveUser(User user);
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
	User getUser(String username);
	List<User> getUsers(Integer pageNo, Integer pageSize);
	void deleteUser(User user);
	void disableUser(User user);
	void changeUserActivity(User user);
	void addAdminRole(User user);
	void removeAdminRole(User user);
	List<User> getUsersSearch(Integer pageNo, Integer pageSize, String name);
	int getUsersSearchCount(String name);
	void removeSuperAdminRole(User user);
	void removeUserRole(User user);
}
