package com.Bookingapp.Bookingapp.Models;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


//Lombok:
@Data
@NoArgsConstructor
@EqualsAndHashCode
//end of lombok
@Entity
@Table(name = "Users")
public class Users implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UsersID")
	private Long id;
	@Column(name = "FirstName")
	private String firstName;
	@Column(name = "LastName")
	private String lastName;
	@Column(name = "Email")
	private String email;
	@Column(name = "Password")
	private String password;
	@Column(name = "Telephone")
	private String telephone; //String type incase of "+" symbol eg "+38164.."
	@Column(name = "DateOpen")
	private Date dateOpen;
	@Column(name = "UserActive")
	private Boolean userActive;
	@ManyToOne
	@JoinColumn(name = "WorkingUnitID", nullable = false)
	private WorkingUnit workingUnit;
	@Enumerated(EnumType.STRING)
	private Role role;

	
	private static final long serialVersionUID = 1L;
		
	
	public Users(String firstName, String lastName, String email, String password, 
					String telephone, Date dateOpen, Boolean userActive, 
					WorkingUnit workingUnit, Role appUserRole) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.telephone = telephone;
		this.dateOpen = dateOpen;
		this.userActive = userActive;
		this.workingUnit = workingUnit;
		this.role = appUserRole;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
		return Collections.singletonList(authority);
	}

	@Override
	public String getPassword() { //done
		return password;
	}

	@Override
	public String getUsername(){ //done
		return email;
	}

	@Override
	public boolean isAccountNonExpired() { //will not implement
		return false;
	}

	@Override
	public boolean isAccountNonLocked() { //done
		return !userActive;
	}

	@Override
	public boolean isCredentialsNonExpired() { //will not implement
		return true;
	}

	@Override
	public boolean isEnabled() { //done
		return userActive;
	}
}