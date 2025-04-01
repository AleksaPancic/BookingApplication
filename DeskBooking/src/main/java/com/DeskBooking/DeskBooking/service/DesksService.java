package com.DeskBooking.DeskBooking.service;

import java.util.Optional;

import com.DeskBooking.DeskBooking.model.Desk;

public interface DesksService {
	public Desk saveDesk(Desk desk);
	public Optional<Desk> findById(Long id);
	public void removeDesk(String name);
	void changeActivity(String name, Boolean activity);
}
