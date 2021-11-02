package com.Bookingapp.Bookingapp.Models;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode

@Entity
@Table(name = "WorkingUnit")
public class WorkingUnit {
	@Column(name = "WorkingUnitID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "Name")
	private String name;
	@Enumerated(EnumType.STRING)
	private City city;
	@Column(name = "Address")
	private String address;
	@Column(name = "Telephone")
	private String telephone;
	@Column(name = "Map")
	private Blob map;
}
