package com.DeskBooking.deskbooking.registration.token;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

import com.DeskBooking.deskbooking.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
	
	@SequenceGenerator(
			name = "confirmation_token_sequence", 
			sequenceName = "confirmation_token_sequence", 
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "confirmation_token_sequence"
	)
	private Long id;
	
	@Column(nullable = false)
	private String token;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime expiresAt;
	
	private LocalDateTime confirmedAt;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "user_id")
	private User user;
	
	public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, User user) {
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiredAt;
		this.user = user;
	}
	
	
}
