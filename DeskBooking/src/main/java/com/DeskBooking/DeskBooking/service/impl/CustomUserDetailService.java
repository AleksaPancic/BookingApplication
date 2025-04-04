package com.DeskBooking.DeskBooking.service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.DeskBooking.DeskBooking.service.EmailSenderService;
import com.DeskBooking.DeskBooking.service.HtmlData;
import com.DeskBooking.DeskBooking.service.UsersService;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.DeskBooking.DeskBooking.model.Mail;
import com.DeskBooking.DeskBooking.model.Role;
import com.DeskBooking.DeskBooking.model.User;
import com.DeskBooking.DeskBooking.model.WorkingUnit;
import com.DeskBooking.DeskBooking.repository.RoleRepository;
import com.DeskBooking.DeskBooking.repository.UsersRepository;
import com.DeskBooking.DeskBooking.repository.WorkingUnitsRepository;
import com.DeskBooking.DeskBooking.exception.UserNotFoundException;
import com.DeskBooking.DeskBooking.exception.UsernameAlredyTakenException;
import com.DeskBooking.DeskBooking.registration.token.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomUserDetailService implements UserDetailsService, UsersService {

   
    private final UsersRepository usersRepository;
	private final RoleRepository roleRepository;
	private final WorkingUnitsRepository workingUnitsRepository;
	private final PasswordEncoder passwordEncoder;
	private final ConfirmationTokenService confirmationTokenService;
	private final EmailSenderService emailSenderService;
	private final HtmlData htmlData;
	private String password;
    
    public String signUpUser(User user)
    		throws UsernameAlredyTakenException {
		User userExist = usersRepository.findByUsername(user.getUsername());
		
		if (userExist != null) {
			throw new UsernameAlredyTakenException(user.getUsername());
		}
		
		
		
		usersRepository.save(user);
		
		
		String token = UUID.randomUUID().toString();	
		ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusHours(2), user);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		
		return token;
	}
    
    
    

    
    
   private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles)
   {
	   
	  return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	   
   }
   
	@Override
	
	public User saveUser(User user) {
		
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
		User user = usersRepository.findByUsername(username);
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
	}
	@Override
	public User getUser(String username) {
		log.info("Fetching user {} from database", username);
		if(usersRepository.findByUsername(username) == null) {
			throw new UserNotFoundException("User not found.. ");
		}
		return usersRepository.findByUsername(username);
	}
	@Override
	public List<User> getUsers(Integer pageNo, Integer pageSize) { //ne treba , po aktivnim korisnicimam ne aktivni page
		
		 Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("firstName"));
		 Page<User> pagedResult = usersRepository.findAll(paging);
		
		 
		log.info("Fetching all users from database, user count: {}", usersRepository.count());
		if(pagedResult.hasContent()) {
            return pagedResult.getContent(); 
        } else {
            return new ArrayList<User>();
        }
	}

	@Override
	public void deleteUser(User user) {
		usersRepository.delete(user);
	}

	@Override
	public void disableUser(User user) {
		user.setUserActive(false);
	}

	@Override
	public void changeUserActivity(User user) {
		if (user.getUserActive()) {
			user.setUserActive(false);
		}
		else {
			user.setUserActive(true);
		}
	}

	@Override
	public void addAdminRole(User user) {
		Role adminRole = roleRepository.findByName("ROLE_ADMIN");
		Role userRole = roleRepository.findByName("ROLE_USER");
		List<Role> userRoles = user.getRoles();
		userRoles.remove(userRole);
		userRoles.add(adminRole);
		user.setRoles(userRoles);
	}

	@Override
	public void removeAdminRole(User user) {
		Role adminRole = roleRepository.findByName("ROLE_ADMIN");
		List<Role> userRoles = user.getRoles();
		userRoles.remove(adminRole);
		user.setRoles(userRoles);
	}
	
	@Override
	public void removeSuperAdminRole(User user) {
		Role superAdminRole = roleRepository.findByName("ROLE_ENJOYING_ADMIN");
		List<Role> userRoles = user.getRoles();
		userRoles.remove(superAdminRole);
		user.setRoles(userRoles);
	}
	
	@Override
	public void removeUserRole(User user) {
		Role userRole = roleRepository.findByName("ROLE_USER");
		List<Role> userRoles = user.getRoles();
		userRoles.remove(userRole);
		user.setRoles(userRoles);
	}
	
	@Override
	public List<User> getUsersSearch(Integer pageNo, Integer pageSize, String name) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("firstName"));
		Page<User> pagedResult = usersRepository.findAllByBothName(name, paging);

		int numOfSearchedUsers = usersRepository.findAllByBothNameCount(name);

		log.info("Fetching users with firstName/lastName like " + name + " from database, user count : {}", numOfSearchedUsers);
		if(pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<User>();
		}
	}

	@Override
	public int getUsersSearchCount(String name) {
		return usersRepository.findAllByBothNameCount(name);
	}

	@Override
	public void addWorkingUnitToUser(String username, String workingunitname) {
		log.info("Adding Working Unit {} to the user {}", workingunitname , username);
		User user = usersRepository.findByUsername(username);
		WorkingUnit workingUnit =  workingUnitsRepository.findByUnitName(workingunitname);
		user.setWorkingUnit(workingUnit);
	}
	
	

	@Override
	public void addTelephone(String username, String telephone) {
		log.info("Adding telephone {} to the user {}" , username, telephone);
		User user = usersRepository.findByUsername(username);
		user.setTelephone(telephone);
	}

	@Override
	public void addEmail(String username, String email) {
		log.info("Adding Email {} to the user {}" , email, username);
		User user = usersRepository.findByUsername(username);
		user.setEmail(email);

	}

	@Override
	public void addFirstName(String username, String firstname) {
		log.info("Adding First Name {} to the user {}" , firstname, username);
		User user = usersRepository.findByUsername(username);
		user.setFirstName(firstname);
	}

	@Override
	public void addLastName(String username, String lastname) {
		log.info("Adding Last Name {} to the user {}" , lastname, username);
		User user = usersRepository.findByUsername(username);
		user.setLastName(lastname);
	}

	@Override
	public void ChangeActivityForUser(String username, Boolean active) {
		log.info("Changing activity {} for the user {}" , active, username);
		User user = usersRepository.findByUsername(username);
		user.setUserActive(active);
	}
	
	@Override
	public void addUsername(String usernameact, String username) {
		log.info("Changing Username {} for the user {}" , username, usernameact);
		User user = usersRepository.findByUsername(usernameact);
		user.setUsername(username);
	}
	
	@Override
	public void addPassword(String username, String password) {
		log.info("Changing password for the user {}" , username);
		User user = usersRepository.findByUsername(username);
		user.setPassword(passwordEncoder.encode(password));
	}
	
	@Override
	public void changeAll(String usernameact, String firstName, String lastName,
			String telephone, String email) {
		
		log.info("Changing profile for the user {} **firstname,lastname"
				+ ",telephone,email**" , usernameact);
		
		User user = usersRepository.findByUsername(usernameact);
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
	
    public String confirmReset(User user) {
		User userExist = usersRepository.findByUsername(user.getUsername());
		
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
		
		User user = new User();
		user = usersRepository.findByUsername(username);
		
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = usersRepository.findByUsername(username);
		ArrayList<GrantedAuthority> authorities = (ArrayList<GrantedAuthority>) user.getRoles().stream().map(role -> {
			GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
			return authority;
		}).collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

	@Override
	public User fetchCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = usersRepository.findByUsername(username);
		return user;
	}
}