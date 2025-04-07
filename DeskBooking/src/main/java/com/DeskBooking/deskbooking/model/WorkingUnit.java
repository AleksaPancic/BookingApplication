package com.DeskBooking.deskbooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "Working_Units")
public class WorkingUnit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "WorkingUnit_ID")
	private Long id;
	
	@Column(name = "WorkingUnitName")
	private String unitName;
	
	@Enumerated(EnumType.STRING)
	private City city;
	
	@Column(name = "WorkingUnitAddress")
	private String unitAddress;
	
	@Column(name = "WorkingUnitPhone")
	private String unitPhone;
	
	@Column(name = "WorkingUnitMap")
	private String map;
	
	@Column(name = "WorkingUnitParkingMap")
	private String parkingMap;
	
	public WorkingUnit(String unitName, City city, String unitAddress, String unitPhone, String map, String parkingMap) {
		this.parkingMap = parkingMap;
		this.unitName = unitName;
		this.city = city;
		this.unitAddress = unitAddress;
		this.unitPhone = unitPhone;
		this.map = map;
	}	
}
