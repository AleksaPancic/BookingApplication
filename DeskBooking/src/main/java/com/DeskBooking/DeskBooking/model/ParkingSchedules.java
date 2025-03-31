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
@Table(name = "Parking_Schedules")
public class ParkingSchedules {
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
	@JoinColumn(name = "Parking_Id", nullable = true)
	private Parking parking;
	
	public ParkingSchedules(boolean status, Date dateFrom, Date dateTo, User user, Parking parking) {
		this.status = status;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.user = user;
		this.parking = parking;
	}
	
	
	
	
}
