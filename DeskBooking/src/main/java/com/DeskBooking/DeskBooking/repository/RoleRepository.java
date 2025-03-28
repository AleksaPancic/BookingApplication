package com.DeskBooking.DeskBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.model.Roles;

@Repository
@Transactional(readOnly = true)
public interface RoleRepository extends JpaRepository<Roles, Long>{
	Roles findByName(String name);
}
