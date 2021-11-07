package com.DeskBooking.DeskBooking.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "User_ID")
	private Long id;
	@Column(name = "Username")
	private String username;
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
	@JoinColumn(name = "WorkingUnit_ID", nullable = true)
	private WorkingUnits workingUnit;
	@ManyToMany(fetch = FetchType.EAGER) //to load all the roles when user is loaded "fetch"
	private Collection<Role> roles = new ArrayList<>();

	public Users(String username, String firstName, String lastName, String email, String password,
			String telephone,Date dateOpen, Boolean userActive, /*WorkingUnits workingUnit,*/
			Collection<Role> roles) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.telephone = telephone;
		this.dateOpen = dateOpen;
		this.userActive = userActive;
		//this.workingUnit = workingUnit;
		this.roles = roles;
	}
}