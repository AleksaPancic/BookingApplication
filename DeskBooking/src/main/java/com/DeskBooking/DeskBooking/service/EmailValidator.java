package com.DeskBooking.DeskBooking.service;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class EmailValidator implements Predicate<String> {

	@Override
	public boolean test(String t) { //This can be done with entity validation lol, delete this sh1t
		// TODO Regex to validate email?
		return true;
	}

}
