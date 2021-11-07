package com.DeskBooking.DeskBooking.Models;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "Schedules")
public class Schedules {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Schedule_Id")
	private long id;
	@Column(name = "Status")
	private boolean status;
	@Column(name = "DateFrom")
	private Date dateFrom;
	@Column(name = "DateTo")
	private Date dateTo;
	@ManyToOne
	@JoinColumn(name = "User_ID", nullable = false)
	private Users user;
	@ManyToOne
	@JoinColumn(name = "Desk_Id", nullable = false)
	private Desks desk;
	public Schedules(boolean status, Date dateFrom, Date dateTo, Users user, Desks desk) {
		this.status = status;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.user = user;
		this.desk = desk;
	}
	
	
	
	
}
