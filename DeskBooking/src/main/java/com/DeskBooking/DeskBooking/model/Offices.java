package com.DeskBooking.DeskBooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
    private Long id;
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