package com.DeskBooking.deskbooking.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.deskbooking.model.User;

@Repository
@Transactional(readOnly = true)
public interface UsersRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {
	User findByUsername(String username); //refactor to be optional, goddamn it

	@Query("select u from User u where (upper(u.firstName) like concat('%', upper(:name),'%')) or (upper(u.lastName) like concat('%', upper(:name),'%'))")
	Page<User> findAllByBothName(String name, Pageable page);

	@Query("select count(u) from User u where (upper(u.firstName) like concat('%', upper(:name),'%')) or (upper(u.lastName) like concat('%', upper(:name),'%'))")
	int findAllByBothNameCount(String name);

	List<User> findAllByFirstName(String firstName, Pageable paging);
}
