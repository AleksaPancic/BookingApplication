package com.Bookingapp.Bookingapp.Service;

import org.springframework.stereotype.Service;

import com.Bookingapp.Bookingapp.Models.Users;
import com.Bookingapp.Bookingapp.Repository.AppUserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class appUserService implements UserDetailsService, appUserServiceInt {

	@Autowired
	private  AppUserRepository appUserRepository;
	
	
	public appUserService(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return appUserRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid user with email" + email));
	}


	@Override
	public List<Users> getAllUsers() {
		return appUserRepository.findAll();
	}


	@Override
	public long countUsers() {
		return appUserRepository.count();
	}


}
