package com.orderflow.auth.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.orderflow.auth.domain.dto.request.UserRequest;
import com.orderflow.auth.domain.dto.response.UserResponse;
import com.orderflow.auth.domain.entity.Role;
import com.orderflow.auth.domain.entity.User;
import com.orderflow.auth.enums.UserStatus;
import com.orderflow.auth.exception.EmailAlreadyExistsException;
import com.orderflow.auth.repository.RoleRepository;
import com.orderflow.auth.repository.UserRepository;
import com.orderflow.auth.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserResponse createUser(UserRequest request) {
		if (userRepository.existsByEmail(request.email())) {
			throw new EmailAlreadyExistsException();
		}

		Role customerRole = roleRepository.findByName("CUSTOMER")
				.orElseThrow(() -> new IllegalStateException("CUSTOMER role not seeded"));

		User user = User.builder()
				.fullName(request.fullName())
				.email(request.email())
				.passwordHash(passwordEncoder.encode(request.password()))
				.emailVerified(false)
				.status(UserStatus.PENDING_VERIFICATION)
				.roles(Set.of(customerRole))
				.build();

		userRepository.save(user);
		return mapToResponseDTO(user);
	}

	@Override
	public UserResponse getUser(String id) {
		User user = userRepository.findById(id).orElseThrow();

		return mapToResponseDTO(user);
	}

	private UserResponse mapToResponseDTO(User user) {
		Set<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

		return UserResponse.builder()
				.id(user.getId())
				.email(user.getEmail())
				.fullName(user.getFullName())
				.status(user.getStatus().name())
				.emailVerified(user.getEmailVerified())
				.roles(roleNames)
				.createdAt(user.getCreatedAt())
				.updatedAt(user.getUpdatedAt())
				.build();
	}
}
