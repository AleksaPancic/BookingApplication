package com.DeskBooking.deskbooking.service;

import java.util.Optional;

import com.DeskBooking.deskbooking.model.Offices;

public interface OfficesSevice {
	public void changeActivity(String name, Boolean activity);
	public void removeOffice(String name);
	public Offices saveOffice(Offices office);
	public Optional<Offices> findById(Long id);
	void removeDesks(String name);
}
