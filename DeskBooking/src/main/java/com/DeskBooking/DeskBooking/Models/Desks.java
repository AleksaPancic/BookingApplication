package com.DeskBooking.DeskBooking.Models;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "Desks")
public class Desks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Desk_Id")
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Available")
    private Boolean available;
    @ManyToOne
    @JoinColumn(name = "Office_Id", nullable = true)
    private Offices office;

    public Desks(String name, Boolean available, Offices office) {
        this.name = name;
        this.available = available;
        this.office = office;
    }

    public boolean isAvailable() { return available; }
}
