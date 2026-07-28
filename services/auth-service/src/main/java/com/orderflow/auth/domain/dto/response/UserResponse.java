package com.orderflow.auth.domain.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Builder;

@Builder
public class UserResponse {

	String id;
	String email;
	String fullName;
	String status;
	boolean emailVerified;
	Set<String> roles;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;
	
}
