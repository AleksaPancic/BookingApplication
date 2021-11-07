package com.DeskBooking.DeskBooking.Models;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "Working_Units")
public class WorkingUnits {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "WorkingUnit_ID")
	private long id;
	
	@Column(name = "WorkingUnitName")
	private String unitName;
	
	@Enumerated(EnumType.STRING)
	private City city;
	
	@Column(name = "WorkingUnitAddress")
	private String unitAddress;
	
	@Column(name = "WorkingUnitPhone")
	private String unitPhone;
	
	//map BLOB?
	@Column(name = "WorkingUnitMap")
	private Blob map;
	
	
	public WorkingUnits(String unitName, String unitAddress, String unitPhone, Blob map) {
		this.unitName = unitName;
		this.unitAddress = unitAddress;
		this.unitPhone = unitPhone;
		this.map = map;
	}	
}
