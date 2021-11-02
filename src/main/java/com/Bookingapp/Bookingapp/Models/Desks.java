package com.Bookingapp.Bookingapp.Models;


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
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Desks")
public class Desks {
	@Column(name = "DeskID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "Name")
	private String name;
	@ManyToOne
	@JoinColumn(name = "OfficesID")
	private Offices office;
	//private Set<Offices> offices = new HashSet<>();
	@Column(name = "Available")
	private Boolean available;
}
