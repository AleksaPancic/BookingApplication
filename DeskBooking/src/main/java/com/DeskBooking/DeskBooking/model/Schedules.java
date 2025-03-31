package com.DeskBooking.DeskBooking.model;



import java.util.Date;

import jakarta.persistence.*;

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
	private Long id;
	@Column(name = "Status")
	private Boolean status;
	@Column(name = "DateFrom")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFrom;
	@Column(name = "DateTo")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTo;
	@ManyToOne
	@JoinColumn(name = "User_ID", nullable = true)
	private User user;
	@ManyToOne
	@JoinColumn(name = "Desk_Id", nullable = true)
	private Desks desk;
	
	public Schedules(boolean status, Date dateFrom, Date dateTo, User user, Desks desk) {
		this.status = status;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.user = user;
		this.desk = desk;
	}
	
	
	
	
}
