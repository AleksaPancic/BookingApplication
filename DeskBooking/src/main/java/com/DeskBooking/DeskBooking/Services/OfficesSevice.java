package com.DeskBooking.DeskBooking.Services;

import java.util.Optional;

import com.DeskBooking.DeskBooking.Models.Offices;

public interface OfficesSevice {
	public void changeActivity(String name, Boolean activity);
	public void removeOffice(String name);
	public Offices saveOffice(Offices office);
	public Optional<Offices> findById(Long id);
	void removeDesks(String name);
}
