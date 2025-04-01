package com.DeskBooking.DeskBooking.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "Users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "User_ID")
	private Long id;

	@Column(name = "Username", unique = true)
	@NotBlank(message = "Username can't be empty")
	private String username;
	
	@Column(name = "FirstName")
	@NotBlank(message = "First name can't be empty")
	private String firstName;
	
	@Column(name = "LastName")
	@NotBlank(message = "Last name can't be empty")
	private String lastName;
	
	@Column(name = "Email")
	@Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@enjoying.rs")
	@NotBlank(message = "Email can't be empty")
	private String email;
	
	@Column(name = "Password")
	@NotBlank(message = "Password can't be empty")
	private String password;
	
	@Column(name = "Telephone")
	private String telephone; //String type incase of "+" symbol eg "+38164.."
	
	@Column(name = "DateOpen")
	private Date dateOpen;
	
	@Column(name = "UserActive")
	private Boolean userActive;
	
	@ManyToOne
	@JoinColumn(name = "WorkingUnit_ID", nullable = true)
	private WorkingUnit workingUnit;
	
	@ManyToMany(fetch = FetchType.EAGER) //to load all the roles when user is loaded "fetch"
	private List<Role> roles = new ArrayList<>();

	public User(String username, String firstName, String lastName, String email, String password,
				String telephone, Date dateOpen, Boolean userActive, WorkingUnit workingUnit,
				List<Role> roles) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.telephone = telephone;
		this.dateOpen = dateOpen;
		this.userActive = userActive;
		this.workingUnit = workingUnit;
		this.roles = roles;
	}
}