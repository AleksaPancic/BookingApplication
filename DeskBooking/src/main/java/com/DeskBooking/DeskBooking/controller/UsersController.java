package com.DeskBooking.DeskBooking.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.DeskBooking.DeskBooking.controller.request.PasswordToUserFormRequest;
import com.DeskBooking.DeskBooking.controller.request.RoleToUserFormRequest;
import com.DeskBooking.DeskBooking.controller.request.UpdateFormRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.DeskBooking.DeskBooking.exception.InvalidInputException;
import com.DeskBooking.DeskBooking.model.Role;
import com.DeskBooking.DeskBooking.model.User;
import com.DeskBooking.DeskBooking.service.impl.CustomUserDetailService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor

public class UsersController {
	
	
	private final CustomUserDetailService usersService;
	
	
	@GetMapping("/view")
	public ResponseEntity<User> viewTestProfile(Principal principal){
		return ResponseEntity.ok().body(usersService.getUser(principal.getName()));
	}
	
			
	@PostMapping("/update") 
	public ResponseEntity<?> updateUser(@RequestBody UpdateFormRequest form, Principal principle) throws Exception{
		if(form.getEmail().chars().count() > 0 && form.getFirstName().chars().count() > 0 && form.getLastName().chars().count() > 0 &&
				form.getTelephone().chars().count() > 0 && form.getWorkingUnit().chars().count() > 0 && form.getEmail().contains("@enjoying.rs"))
		{
				usersService.changeAll(principle.getName(), form.getFirstName(), form.getLastName(), 
						form.getTelephone(), form.getEmail());
				if(form.getWorkingUnit().contains("Nis Office") || form.getWorkingUnit().contains("Beograd Office") || form.getWorkingUnit().contains("Kragujevac Office")) {
				usersService.addWorkingUnitToUser(principle.getName(), form.getWorkingUnit());
				}
				else {
					throw new InvalidInputException("Invalid input, change your input to match one of the following: Nis Office, Beograd Office, Kragujec Office");
				}
				return ResponseEntity.ok().body(usersService.getUser(principle.getName()));
		}
		else {
			throw new InvalidInputException("Invalid input all fields must be filled and your email must be part of enjoying team!");
		}
	}
		
	@PostMapping("/update/password")
	public ResponseEntity<?> updatePassword(@RequestBody PasswordToUserFormRequest form, Principal principal) throws Exception{
		if(form.getPassword().chars().count() > 4) {
		usersService.addPassword(principal.getName(), form.getPassword());
		return ResponseEntity.ok().build();
		}else {
			throw new InvalidInputException("Password must be at least 4 characters long");
		}
	}
			
	@GetMapping("view/user/list")
	public ResponseEntity<List<User>> getUsers(
			@RequestParam(defaultValue = "0") Integer pageNo, 
	        @RequestParam(defaultValue = "5") Integer pageSize){
		 List<User> list = usersService.getUsers(pageNo, pageSize);
	 return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}

	@GetMapping("/view/user/search")
	public ResponseEntity<List<User>> getUsersSearch(
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize,
			@RequestParam String name) {

		List<User> list = usersService.getUsersSearch(pageNo, pageSize, name);
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	
	//return number of users
	@GetMapping("view/users/number")
	public ResponseEntity<Integer> getNumOfUsers(){
		Integer num = usersService.getNumOfUsers();
		return new ResponseEntity<Integer>(num, HttpStatus.OK);
	}
		
	@PostMapping("/update/user")
	public ResponseEntity<User> saveUser(@RequestBody User user){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/update").toUriString());
		return ResponseEntity.created(uri).body(usersService.saveUser(user));
	}
	
	@PostMapping("/update/user/role")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserFormRequest form){
		usersService.addRoleToUser(form.getUsername(), form.getRoleName());
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/update/user/password")
	public ResponseEntity<?> addPasswordToUser(@RequestBody PasswordToUserFormRequest form){
		usersService.addPassword(form.getUsername(), form.getPassword());
		return ResponseEntity.ok().build();
	}
		
	//REFRESH TOKEN
	
	 @GetMapping("/token/refresh")
	    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
	        String authorizationHeader = request.getHeader(AUTHORIZATION);
	      
	            try {
	                String refresh_token = authorizationHeader.substring("Bearer ".length());
	                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
	                JWTVerifier verifier = JWT.require(algorithm).build();
	                DecodedJWT decodedJWT = verifier.verify(refresh_token);
	                String username = decodedJWT.getSubject();
	                User user = usersService.getUser(username);
	                String access_token = JWT.create()
	                        .withSubject(user.getUsername())
	                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
	                        .withIssuer(request.getRequestURL().toString())
	                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
	                        .sign(algorithm);
	                Map<String, String> tokens = new HashMap<>();
	                tokens.put("access_token", access_token);
	                tokens.put("refresh_token", refresh_token);
	                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	            }catch (Exception exception) {
	                response.setHeader("error", exception.getMessage());
	                response.setStatus(UNAUTHORIZED.value());
	                Map<String, String> error = new HashMap<>();
	                error.put("error_message", exception.getMessage());
	                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
	                new ObjectMapper().writeValue(response.getOutputStream(), error);
	            }
	        
	    }
	
}