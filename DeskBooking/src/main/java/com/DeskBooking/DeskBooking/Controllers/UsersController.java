package com.DeskBooking.DeskBooking.Controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.DeskBooking.DeskBooking.Models.Role;
import com.DeskBooking.DeskBooking.Models.Users;
import com.DeskBooking.DeskBooking.Services.UsersService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class UsersController {
	private final UsersService usersService;
	
	@GetMapping("/users")
	public ResponseEntity<List<Users>> getUsers(){
		return ResponseEntity.ok().body(usersService.getUsers());
	}
	
	@PostMapping("/user/save")
	public ResponseEntity<Users> saveUser(@RequestBody Users user){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
		return ResponseEntity.created(uri).body(usersService.saveUser(user));
	}
	
	@PostMapping("/role/save")
	public ResponseEntity<Role> saveRole(@RequestBody Role role){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
		return ResponseEntity.created(uri).body(usersService.saveRole(role));
	}
	
	@PostMapping("/role/addtouser")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
		usersService.addRoleToUser(form.getUsername(), form.getRoleName());
		return ResponseEntity.ok().build();
	}
}
@Data
class RoleToUserForm{
	private String username;
	private String roleName;
}
