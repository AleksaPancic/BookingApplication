package com.DeskBooking.DeskBooking.registration.Token;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public interface ConfirmationTokenService {
	
	public void saveConfirmationToken(ConfirmationToken token);
	public Optional<ConfirmationToken> getToken(String token);
	public int setConfirmedAt(String token);
	
}