package com.DeskBooking.DeskBooking.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.Models.Role;
import com.DeskBooking.DeskBooking.Models.Users;
import com.DeskBooking.DeskBooking.Models.WorkingUnits;
import com.DeskBooking.DeskBooking.Repositories.RoleRepository;
import com.DeskBooking.DeskBooking.Repositories.UsersRepository;
import com.DeskBooking.DeskBooking.Repositories.WorkingUnitsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsersServiceImpl implements UserDetailsService, UsersService {

	private final UsersRepository usersRepository;
	private final RoleRepository roleRepository;
	private final WorkingUnitsRepository workingUnitsRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = usersRepository.findByUsername(username);
		if(user == null) {
			log.error("User not found in the database");
			throw new UsernameNotFoundException("User not found in the database");
		} else {
			log.info("User found in database: {}", username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

	@Override
	public Users saveUser(Users user) {
		log.info("Saving new user {} to the database", user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return usersRepository.save(user);
	}
	@Override
	public Role saveRole(Role role) {
		log.info("Saving new role {} to the database", role.getName());
		return roleRepository.save(role);
	}
	@Override
	public void addRoleToUser(String username, String roleName) {
		log.info("Adding role {} to the user {}", roleName , username);
		Users user = usersRepository.findByUsername(username);
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
	}
	@Override
	public Users getUser(String username) {
		log.info("Fetching user {} from database", username);
		return usersRepository.findByUsername(username);
	}
	@Override
	public List<Users> getUsers() {
		log.info("Fetching all users from database");
		return usersRepository.findAll();
	}

	@Override
	public void addWorkingUnitToUser(String username, String workingunitname) {
		log.info("Adding Working Unit {} to the user {}", workingunitname , username);
		Users user = usersRepository.findByUsername(username);
		WorkingUnits workingUnits =  workingUnitsRepository.findByunitName(workingunitname);
		user.setWorkingUnit(workingUnits);
	}

	@Override
	public void addTelephone(String username, String telephone) {
		log.info("Adding telephone {} to the user {}" , username, telephone);
		Users user = usersRepository.findByUsername(username);
		user.setTelephone(telephone);
	}

	@Override
	public void addEmail(String username, String email) {
		log.info("Adding Email {} to the user {}" , email, username);
		Users user = usersRepository.findByUsername(username);
		user.setEmail(email);

	}

	@Override
	public void addFirstName(String username, String firstname) {
		log.info("Adding First Name {} to the user {}" , firstname, username);
		Users user = usersRepository.findByUsername(username);
		user.setFirstName(firstname);
	}

	@Override
	public void addLastName(String username, String lastname) {
		log.info("Adding Last Name {} to the user {}" , lastname, username);
		Users user = usersRepository.findByUsername(username);
		user.setLastName(lastname);
	}

	@Override
	public void ChangeActivityForUser(String username, Boolean active) {
		log.info("Changing activity {} for the user {}" , active, username);
		Users user = usersRepository.findByUsername(username);
		user.setUserActive(active);
	}
}
