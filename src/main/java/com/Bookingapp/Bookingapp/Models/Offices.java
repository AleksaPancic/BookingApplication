package com.Bookingapp.Bookingapp.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Ofices")
public class Offices {
	@Column(name = "OfficesID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "Name")
	private String name;
	@OneToOne
	@JoinColumn(name = "WorkingUnitID")
	private WorkingUnit workingUnit;
	@Column(name = "Available")
	private Boolean available;
	
}
