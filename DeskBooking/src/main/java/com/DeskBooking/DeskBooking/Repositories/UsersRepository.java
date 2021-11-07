package com.DeskBooking.DeskBooking.Repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.Models.Users;

@Repository
@Transactional(readOnly = true)
public interface UsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findById(Long id);
	Users findByEmail(String email);
	Users findByUsername(String username);
}
