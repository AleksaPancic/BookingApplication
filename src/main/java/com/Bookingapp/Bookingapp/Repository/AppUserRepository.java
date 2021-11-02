package com.Bookingapp.Bookingapp.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Bookingapp.Bookingapp.Models.Users;

//svi query i logika

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<Users, Long>{
	Optional<Users> findByEmail(String email);
}
