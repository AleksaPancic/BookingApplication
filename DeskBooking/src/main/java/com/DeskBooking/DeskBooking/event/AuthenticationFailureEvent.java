package com.DeskBooking.DeskBooking.event;

import com.DeskBooking.DeskBooking.exception.UnauthenticatedUserException;
import com.DeskBooking.DeskBooking.model.User;
import com.DeskBooking.DeskBooking.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureEvent {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationFailureEvent.class);

    private static final int MAX_AUTHENTICATION_ATTEMPTS = 3;

    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    @EventListener
    public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        User user = usersRepository.findByUsername(authentication.getName());
         if (user.getAuthenticationFailure() > 0) {
            throw new RuntimeException("Authentication failure"); //Custom exception needed
        } else if (user != null && user.getAuthenticationFailure() > 0) {
            user.setAuthenticationFailure(0);
            usersRepository.save(user);
            logger.info("Reset authentication failures for user: {}", user.getUsername());
        }
    }

    @Transactional
    @EventListener
    public void handleAuthenticationFailure(AbstractAuthenticationFailureEvent event) {
        Authentication authentication = event.getAuthentication();
        String username = authentication.getName();

        try {
            User user = usersRepository.findByUsername(username);
            if (user == null) {
                logger.warn("Authentication attempt for non-existent user: {}", username);
                return;
            }

            int newFailureCount = user.getAuthenticationFailure() + 1;
            user.setAuthenticationFailure(newFailureCount);

            if (newFailureCount >= MAX_AUTHENTICATION_ATTEMPTS) {
                user.setUserActive(false);
                logger.warn("Account locked for user: {} after {} failed attempts",
                        username, MAX_AUTHENTICATION_ATTEMPTS);
            }

            usersRepository.save(user);
            logger.debug("Authentication failure #{} for user: {}",
                    newFailureCount, username);

        } catch (Exception e) {
            logger.error("Error processing authentication failure for user: {}", username, e);
        }
    }
}
