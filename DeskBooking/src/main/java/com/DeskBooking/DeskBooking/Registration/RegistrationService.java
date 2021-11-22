package com.DeskBooking.DeskBooking.Registration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.Models.Mail;
import com.DeskBooking.DeskBooking.Models.Users;
import com.DeskBooking.DeskBooking.Registration.Token.ConfirmationToken;
import com.DeskBooking.DeskBooking.Registration.Token.ConfirmationTokenService;
import com.DeskBooking.DeskBooking.Repositories.RoleRepository;
import com.DeskBooking.DeskBooking.Repositories.WorkingUnitsRepository;
import com.DeskBooking.DeskBooking.Services.CustomUserDetailService;
import com.DeskBooking.DeskBooking.Services.EmailSenderService;
import com.DeskBooking.DeskBooking.Services.EmailValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationService {
	
	private final CustomUserDetailService customUserDetailService; //use signUpUser method
	
	private final RoleRepository roleRepository;
	private final WorkingUnitsRepository workingUnitsRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final EmailSenderService emailSenderService;
	private final ConfirmationTokenService confirmationTokenService;
	private final EmailValidator emailValidator;
	
	public String register(RegistrationRequest request) {
		boolean isValidEmail = emailValidator.test(request.getEmail());
		if(!isValidEmail) {
			throw new IllegalStateException("email not valid");
		}
		
		Date date = new Date();
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		
		//confirmation token
		String token = customUserDetailService.signUpUser(new Users(request.getUsername() ,request.getFirstName(), request.getLastName(), request.getEmail(), 
		encodedPassword, request.getTelephone(), date, false, workingUnitsRepository.findByUnitName(request.getWorkingUnitName()),
		Arrays.asList(roleRepository.findByName("ROLE_USER"))));
		
		//email sender
		Mail mail = new Mail();
		mail.setMailTo(request.getEmail());
		
		String link = "http://localhost:8080/register/confirm?token=" + token;
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", request.getFirstName());
		model.put("link", link);
		mail.setProps(model);
		
		emailSenderService.send(mail);
		
		return token;
		}
		
		@Transactional
		public String confirmToken(String token) {
			ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("Token not found"));
			
			if(confirmationToken.getConfirmedAt() != null) {
				throw new IllegalStateException("Email already confirmed");
			}
			
			LocalDateTime expiredAt = confirmationToken.getExpiresAt();
			
			if (expiredAt.isBefore(LocalDateTime.now())) {
				throw new IllegalStateException("Token expired");
			}
			
			confirmationTokenService.setConfirmedAt(token);
			customUserDetailService.ChangeActivityForUser(confirmationToken.getUser().getUsername(), true);
			
			return "confirmed";
		}
	
}
