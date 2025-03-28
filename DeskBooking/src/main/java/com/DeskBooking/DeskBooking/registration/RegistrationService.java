package com.DeskBooking.DeskBooking.registration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.exception.CapitalLetterException;
import com.DeskBooking.DeskBooking.exception.LowercaseLetterException;
import com.DeskBooking.DeskBooking.exception.PasswordLenghtException;
import com.DeskBooking.DeskBooking.exception.PasswordNumberException;
import com.DeskBooking.DeskBooking.exception.PasswordSpaceException;
import com.DeskBooking.DeskBooking.exception.SpecialCharacterException;
import com.DeskBooking.DeskBooking.model.Mail;
import com.DeskBooking.DeskBooking.model.Users;
import com.DeskBooking.DeskBooking.registration.Token.ConfirmationToken;
import com.DeskBooking.DeskBooking.registration.Token.ConfirmationTokenService;
import com.DeskBooking.DeskBooking.repository.RoleRepository;
import com.DeskBooking.DeskBooking.repository.WorkingUnitsRepository;
import com.DeskBooking.DeskBooking.service.CustomUserDetailService;
import com.DeskBooking.DeskBooking.service.EmailSenderService;
import com.DeskBooking.DeskBooking.service.EmailValidator;
import com.DeskBooking.DeskBooking.service.HtmlData;

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
	private final HtmlData htmlData;
	
	public String register(RegistrationRequest request) throws Exception {
		boolean isValidEmail = emailValidator.test(request.getEmail());
		if(!isValidEmail) {
			throw new IllegalStateException("email not valid");
		}
		
		Date date = new Date();
		
		 String password = (request.getPassword());
 		{
		if(!((password.length() >= 8)
	              && (password.length() <= 30))) {
			throw new PasswordLenghtException();
			}
	    }
	    		
	    		  if (password.contains(" ")) {
	    	            throw new PasswordSpaceException();
	    	        }
	    	        if (true) {
	    	            int count = 0;
	    	  
	    	         
	    	            for (int i = 0; i <= 9; i++) {
	    	  
	    	                
	    	                String str1 = Integer.toString(i);
	    	  
	    	                if (password.contains(str1)) {
	    	                    count = 1;
	    	                }
	    	            }
	    	            if (count == 0) {
	    	                throw new PasswordNumberException();
	    	            }
	    	        }
	    	        
	    	        if (!(password.contains("@") || password.contains("#")
	    	                || password.contains("!") || password.contains("~")
	    	                || password.contains("$") || password.contains("%")
	    	                || password.contains("^") || password.contains("&")
	    	                || password.contains("*") || password.contains("(")
	    	                || password.contains(")") || password.contains("-")
	    	                || password.contains("+") || password.contains("/")
	    	                || password.contains(":") || password.contains(".")
	    	                || password.contains(", ") || password.contains("<")
	    	                || password.contains(">") || password.contains("?")
	    	                || password.contains("|"))) {
	    	              throw new SpecialCharacterException();
	    	              
	    	          }
	    	        
	    	        if (true) {
	    	            int count = 0;
	    	  
	    	            // checking capital letters
	    	            for (int i = 65; i <= 90; i++) {
	    	  
	    	              
	    	                char c = (char)i;
	    	  
	    	                String str1 = Character.toString(c);
	    	                if (password.contains(str1)) {
	    	                    count = 1;
	    	                }
	    	            }
	    	            if (count == 0) {
	    	                throw new CapitalLetterException();
	    	            }
	    	        }
	    	        
	    	        if (true) {
	    	            int count = 0;
	    	  
	    	         
	    	            for (int i = 90; i <= 122; i++) {
	    	  
	    	                // type casting
	    	                char c = (char)i;
	    	                String str1 = Character.toString(c);
	    	  
	    	                if (password.contains(str1)) {
	    	                    count = 1;
	    	                }
	    	            }
	    	            if (count == 0) {
	    	                throw new LowercaseLetterException();
	    	            }
	    	        }
		
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
			
			return htmlData.confirmedRegistration();
		}
	
}
