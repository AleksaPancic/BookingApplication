package com.DeskBooking.DeskBooking.Models;

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
@Table(name = "Offices")
public class Offices {
    @Column(name = "Office_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Name")
    private String name;
    @OneToOne
    @JoinColumn(name = "WorkingUnit_ID")
    private WorkingUnits workingUnit;
    @Column(name = "Available")
    private Boolean available;
	public Offices(String name, WorkingUnits workingUnit, Boolean available) {
		this.name = name;
		this.workingUnit = workingUnit;
		this.available = available;
	}
    
    
    

}