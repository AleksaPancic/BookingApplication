package com.DeskBooking.DeskBooking.Models;

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
    private WorkingUnits workingUnit;
    
	public Parking(String name, Boolean available, WorkingUnits workingUnit) {
		this.name = name;
		this.available = available;
		this.workingUnit = workingUnit;
	}
    
    public boolean isAvailable() {
    	return available;
    }
}
