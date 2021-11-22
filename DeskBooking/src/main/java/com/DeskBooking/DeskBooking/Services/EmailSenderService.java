package com.DeskBooking.DeskBooking.Services;

import org.springframework.stereotype.Component;

import com.DeskBooking.DeskBooking.Models.Mail;

@Component
public interface EmailSenderService {
	
	void send(Mail mail);
}
