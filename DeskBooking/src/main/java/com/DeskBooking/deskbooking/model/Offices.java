package com.DeskBooking.deskbooking.model;

import jakarta.persistence.*;

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
    @ManyToOne
    @JoinColumn(name = "WorkingUnit_ID")
    private WorkingUnit workingUnit;
    @Column(name = "Available")
    private Boolean available;
	public Offices(String name, WorkingUnit workingUnit, Boolean available) {
		this.name = name;
		this.workingUnit = workingUnit;
		this.available = available;
	}
    
    
    

}