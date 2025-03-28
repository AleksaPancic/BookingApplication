package com.DeskBooking.DeskBooking.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.DeskBooking.DeskBooking.model.Mail;
import com.DeskBooking.DeskBooking.model.Roles;
import com.DeskBooking.DeskBooking.model.Users;
import com.DeskBooking.DeskBooking.model.WorkingUnits;
import com.DeskBooking.DeskBooking.repository.RoleRepository;
import com.DeskBooking.DeskBooking.repository.UsersRepository;
import com.DeskBooking.DeskBooking.repository.WorkingUnitsRepository;
import com.DeskBooking.DeskBooking.exception.UserNotFoundException;
import com.DeskBooking.DeskBooking.exception.UsernameAlredyTakenException;
import com.DeskBooking.DeskBooking.registration.Token.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomUserDetailService implements UsersService, UserDetailsService {

   
    private final UsersRepository usersRepository;
	private final RoleRepository roleRepository;
	private final WorkingUnitsRepository workingUnitsRepository;
	private final PasswordEncoder passwordEncoder;
	private final ConfirmationTokenService confirmationTokenService;
	private final EmailSenderService emailSenderService;
	private final EmailValidator emailValidator;
	private final HtmlData htmlData;
	private String password;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Users user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
         if (user.getUserActive() == false) {
        	 throw new UsernameNotFoundException ("User is not active");
        } 
          
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), 
        		mapRolesToAuthorities(user.getRoles()));
      
    }
    
    public String signUpUser(Users user) 
    		throws UsernameAlredyTakenException {
		Users userExist = usersRepository.findByUsername(user.getUsername()); 
		
		if (userExist != null) {
			throw new UsernameAlredyTakenException(user.getUsername());
		}
		
		
		
		usersRepository.save(user);
		
		
		String token = UUID.randomUUID().toString();	
		ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusHours(2), user);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		
		return token;
	}
    
    
    

    
    
   private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Roles> roles)
   {
	   
	  return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	   
   }
   
	@Override
	
	public Users saveUser(Users user) {
		
		log.info("Saving new user {} to the database", user.getUsername());

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return usersRepository.save(user);
	}
	
	@Override
	public Roles saveRole(Roles role) {
		log.info("Saving new role {} to the database", role.getName());
		return roleRepository.save(role);
	}
	@Override
	public void addRoleToUser(String username, String roleName) {
		log.info("Adding role {} to the user {}", roleName , username);
		Users user = usersRepository.findByUsername(username);
		Roles role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
	}
	@Override
	public Users getUser(String username) {
		log.info("Fetching user {} from database", username);
		if(usersRepository.findByUsername(username) == null) {
			throw new UserNotFoundException("User not found.. ");
		}
		return usersRepository.findByUsername(username);
	}
	@Override
	public List<Users> getUsers(Integer pageNo, Integer pageSize) { //ne treba , po aktivnim korisnicimam ne aktivni page
		
		 Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("firstName"));
		 Page<Users> pagedResult = usersRepository.findAll(paging);
		
		 
		log.info("Fetching all users from database, user count: {}", usersRepository.count());
		if(pagedResult.hasContent()) {
            return pagedResult.getContent(); 
        } else {
            return new ArrayList<Users>();
        }
	}

	@Override
	public void deleteUser(Users user) {
		usersRepository.delete(user);
	}

	@Override
	public void disableUser(Users user) {
		user.setUserActive(false);
	}

	@Override
	public void changeUserActivity(Users user) {
		if (user.getUserActive()) {
			user.setUserActive(false);
		}
		else {
			user.setUserActive(true);
		}
	}

	@Override
	public void addAdminRole(Users user) {
		Roles adminRole = roleRepository.findByName("ROLE_ADMIN");
		Roles userRole = roleRepository.findByName("ROLE_USER");
		List<Roles> userRoles = user.getRoles();
		userRoles.remove(userRole);
		userRoles.add(adminRole);
		user.setRoles(userRoles);
	}

	@Override
	public void removeAdminRole(Users user) {
		Roles adminRole = roleRepository.findByName("ROLE_ADMIN");
		List<Roles> userRoles = user.getRoles();
		userRoles.remove(adminRole);
		user.setRoles(userRoles);
	}
	
	@Override
	public void removeSuperAdminRole(Users user) {
		Roles superAdminRole = roleRepository.findByName("ROLE_ENJOYING_ADMIN");
		List<Roles> userRoles = user.getRoles();
		userRoles.remove(superAdminRole);
		user.setRoles(userRoles);
	}
	
	@Override
	public void removeUserRole(Users user) {
		Roles	userRole = roleRepository.findByName("ROLE_USER");
		List<Roles> userRoles = user.getRoles();
		userRoles.remove(userRole);
		user.setRoles(userRoles);
	}
	
	@Override
	public List<Users> getUsersSearch(Integer pageNo, Integer pageSize, String name) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("firstName"));
		Page<Users> pagedResult = usersRepository.findAllByBothName(name, paging);

		int numOfSearchedUsers = usersRepository.findAllByBothNameCount(name);

		log.info("Fetching users with firstName/lastName like " + name + " from database, user count : {}", numOfSearchedUsers);
		if(pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Users>();
		}
	}

	@Override
	public int getUsersSearchCount(String name) {
		return usersRepository.findAllByBothNameCount(name);
	}

	@Override
	public void addWorkingUnitToUser(String username, String workingunitname) {
		log.info("Adding Working Unit {} to the user {}", workingunitname , username);
		Users user = usersRepository.findByUsername(username);
		WorkingUnits workingUnits =  workingUnitsRepository.findByUnitName(workingunitname);
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
	
	@Override
	public void addUsername(String usernameact, String username) {
		log.info("Changing Username {} for the user {}" , username, usernameact);
		Users user = usersRepository.findByUsername(usernameact);
		user.setUsername(username);
	}
	
	@Override
	public void addPassword(String username, String password) {
		log.info("Changing password for the user {}" , username);
		Users user = usersRepository.findByUsername(username);
		user.setPassword(passwordEncoder.encode(password));
	}
	
	@Override
	public void changeAll(String usernameact, String firstName, String lastName,
			String telephone, String email) {
		
		log.info("Changing profile for the user {} **firstname,lastname"
				+ ",telephone,email**" , usernameact);
		
		Users user = usersRepository.findByUsername(usernameact);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setTelephone(telephone);
		user.setEmail(email);
	}

	@Override
	public String getFirstName(String username) {
		log.info("Fetching first name from the user {}" , username);
		return usersRepository.findByUsername(username).getFirstName();
	}

	@Override
	public String getLastName(String username) {
		log.info("Fetching last name from the user {}" , username);
		return usersRepository.findByUsername(username).getLastName();
	}

	@Override
	public String getEmail(String username) {
		log.info("Fetching email from the user {}" , username);
		return usersRepository.findByUsername(username).getEmail();
	}

	@Override
	public String getWorkingUnit(String username) {
		log.info("Fetching workingunit from the user {}" , username);
		return usersRepository.findByUsername(username).getWorkingUnit().getUnitName();
	}

	@Override
	public String getTelephone(String username) {
		log.info("Fetching telephone from the user {}" , username);
		return usersRepository.findByUsername(username).getTelephone();
	}
	
    public String confirmReset(Users user) {
		Users userExist = usersRepository.findByUsername(user.getUsername()); 
		
		if (userExist == null) {
			throw new UsernameNotFoundException("Username is not valid.");
		}		
		String token = UUID.randomUUID().toString();	
		ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusHours(2), user);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		
		return token;
	}
    
	@Override
	public String resetPassword(String username) {
		
		Users user = new Users();
		user = usersRepository.findByUsername(username);
				
		boolean isValidEmail = emailValidator.test(user.getEmail());
		if(!isValidEmail) {
			throw new IllegalStateException("email not valid");
		}
		
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedString = buffer.toString();
	    String token = confirmReset(user);
	    
		//email sender
	    
		Mail mail = new Mail();
		mail.setMailTo(user.getEmail());
		
		Map<String, Object> model = new HashMap<String, Object>();
		password = generatedString;
		
		String link = "http://localhost:8080/reset/confirm?token=" + token;

		model.put("name", user.getFirstName());
		model.put("link", link);
		model.put("password", generatedString);
		mail.setProps(model);
		emailSenderService.sendResetPasswordMail(mail);
		
		return token;
	}
	@Transactional
	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("Token not found"));
		
		if(confirmationToken.getConfirmedAt() != null) {
			throw new IllegalStateException("Password already confirmed");
		}
		
		LocalDateTime expiredAt = confirmationToken.getExpiresAt();
		
		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("Token expired");
		}
		
		confirmationTokenService.setConfirmedAt(token);
		addPassword(confirmationToken.getUser().getUsername(), password);
		return htmlData.resetPassword();
	}

	//return number of users in database
	public Integer getNumOfUsers() {
		Integer num = (int) usersRepository.count();
		return num;
	}
}