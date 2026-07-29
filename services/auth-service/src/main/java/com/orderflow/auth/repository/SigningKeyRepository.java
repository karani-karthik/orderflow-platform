package com.orderflow.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderflow.auth.domain.entity.SigningKey;
import com.orderflow.auth.enums.KeyStatus;

public interface SigningKeyRepository extends JpaRepository<SigningKey, String> {

	List<SigningKey> findByStatusIn(List<KeyStatus> statuses);
	
}
