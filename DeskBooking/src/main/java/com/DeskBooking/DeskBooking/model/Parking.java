package com.DeskBooking.DeskBooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Parking")
public class Parking {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Parking_Id")
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Available")
    private Boolean available;
    @ManyToOne
    @JoinColumn(name = "WorkingUnit_Id")
    private WorkingUnit workingUnit;
    
	public Parking(String name, Boolean available, WorkingUnit workingUnit) {
		this.name = name;
		this.available = available;
		this.workingUnit = workingUnit;
	}
    
    public boolean isAvailable() {
    	return available;
    }
}
