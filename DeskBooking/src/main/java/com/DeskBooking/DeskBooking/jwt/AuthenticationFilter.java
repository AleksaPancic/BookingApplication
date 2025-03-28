package com.DeskBooking.DeskBooking.jwt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
//Class that validates credentials that client sends
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
		
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,  //when user try to login
											    HttpServletResponse response) throws AuthenticationException {
		   	String username = request.getParameter("username");
	        String password = request.getParameter("password");
	        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
	        return authenticationManager.authenticate(authenticationToken);
	}
	
	//function that invokes after attemptAuthentication is succesful
	//This method will create jwt token and send it to a client
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
			       FilterChain chain, Authentication authentication) throws IOException, ServletException {
		User user = (User)authentication.getPrincipal(); //the user that has been succesfuly autenticated so now we can create json token
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); //pokusaj kasnije encrypted string
		String access_token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 10000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		String refresh_token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 100 * 60 * 10000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		//response.setHeader("access_token", access_token);
		//response.setHeader("refresh_token", refresh_token);
		Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	}
}
