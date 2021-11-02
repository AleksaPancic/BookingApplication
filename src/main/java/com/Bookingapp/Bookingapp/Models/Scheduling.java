package com.Bookingapp.Bookingapp.Models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Scheduling {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SchedulingID")
	private long id;
	@Column(name = "DateFrom")
	private Date dateFrom;
	@Column(name = "DateTo")
	private Date dateTo;
	@Column(name = "Status")
	private Boolean status;
	@ManyToOne
	@JoinColumn(name = "UsersID")
	private Users user;
	@ManyToOne
	@JoinColumn(name = "DesksID")
	private Desks desk;
}
