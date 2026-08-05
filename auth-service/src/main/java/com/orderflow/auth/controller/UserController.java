package com.orderflow.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderflow.auth.domain.dto.request.UserRequest;
import com.orderflow.auth.domain.dto.response.ApiResponse;
import com.orderflow.auth.domain.dto.response.UserResponse;
import com.orderflow.auth.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest request,
			HttpServletRequest servletRequest) {

		UserResponse user = userService.createUser(request);
		ApiResponse<UserResponse> response = ApiResponse.success(user, "User registered successfully.",
				servletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable(name = "id") String id,
			HttpServletRequest servletRequest) {

		UserResponse user = userService.getUser(id);
		ApiResponse<UserResponse> response = ApiResponse.success(user, "User retrieved successfully",
				servletRequest.getRequestURI());
		return ResponseEntity.ok(response);
	}

}
