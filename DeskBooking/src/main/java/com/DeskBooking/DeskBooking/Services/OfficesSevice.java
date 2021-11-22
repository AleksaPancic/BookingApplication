package com.DeskBooking.DeskBooking.Services;

import java.util.Optional;

import com.DeskBooking.DeskBooking.Models.Offices;

public interface OfficesSevice {
	public Offices saveOffice(Offices office);
	public Optional<Offices> findById(Long id);
}
