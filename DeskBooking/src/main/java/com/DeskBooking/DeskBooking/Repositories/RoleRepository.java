package com.DeskBooking.DeskBooking.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DeskBooking.DeskBooking.Models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
 Role findByName(String name);
}
