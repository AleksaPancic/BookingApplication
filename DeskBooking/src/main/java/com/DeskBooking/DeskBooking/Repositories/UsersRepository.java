package com.DeskBooking.DeskBooking.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.Models.Users;

@Repository
@Transactional(readOnly = true)
public interface UsersRepository extends JpaRepository<Users, Long>, PagingAndSortingRepository<Users, Long> {
	Users findByUsername(String username);
}
