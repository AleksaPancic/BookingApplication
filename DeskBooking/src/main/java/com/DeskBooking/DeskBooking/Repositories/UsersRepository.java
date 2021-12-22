package com.DeskBooking.DeskBooking.Repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.Models.Users;

@Repository
@Transactional(readOnly = true)
public interface UsersRepository extends JpaRepository<Users, Long>, PagingAndSortingRepository<Users, Long> {
	Users findByUsername(String username);

	@Query("select u from Users u where (upper(u.firstName) like concat('%', upper(:name),'%')) or (upper(u.lastName) like concat('%', upper(:name),'%'))")
	Page<Users> findAllByBothName(String name, Pageable page);

	@Query("select count(u) from Users u where (upper(u.firstName) like concat('%', upper(:name),'%')) or (upper(u.lastName) like concat('%', upper(:name),'%'))")
	int findAllByBothNameCount(String name);

	List<Users> findAllByFirstName(String firstName, Pageable paging);
}
