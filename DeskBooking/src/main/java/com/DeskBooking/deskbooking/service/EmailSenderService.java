package com.DeskBooking.deskbooking.service;

import org.springframework.stereotype.Component;

import com.DeskBooking.deskbooking.model.Mail;

@Component
public interface EmailSenderService {
	
	void send(Mail mail);
	void sendResetPasswordMail(Mail mail);
}
