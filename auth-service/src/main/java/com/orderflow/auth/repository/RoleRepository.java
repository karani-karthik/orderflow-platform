package com.orderflow.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderflow.auth.domain.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String>{

	Optional<Role> findByName(String name);
	
}
