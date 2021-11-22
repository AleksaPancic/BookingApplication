package com.DeskBooking.DeskBooking.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.DeskBooking.DeskBooking.Models.Roles;

@Repository
@Transactional(readOnly = true)
public interface RoleRepository extends JpaRepository<Roles, Long>{
	Roles findByName(String name);
}
