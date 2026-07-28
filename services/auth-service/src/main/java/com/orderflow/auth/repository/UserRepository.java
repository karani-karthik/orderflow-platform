package com.orderflow.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderflow.auth.domain.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	boolean existsByEmail(String email);

}
