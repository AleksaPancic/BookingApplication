package com.DeskBooking.DeskBooking.service;

import java.util.Optional;

import com.DeskBooking.DeskBooking.model.Desks;

public interface DesksService {
	public Desks saveDesk(Desks desk);
	public Optional<Desks> findById(Long id);
	public void removeDesk(String name);
	void changeActivity(String name, Boolean activity);
}
