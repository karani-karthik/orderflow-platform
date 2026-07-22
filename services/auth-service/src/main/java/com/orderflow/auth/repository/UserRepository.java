package com.orderflow.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderflow.auth.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
