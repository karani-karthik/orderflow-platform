package com.orderflow.auth.domain.entity;

import com.orderflow.auth.enums.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AuditModel {

	@Column(unique = true, nullable = false)
	private String email;

	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@Column(name = "first_name", length = 120)
	private String firstName;

	@Column(name = "last_name", length = 120)
	private String lastName;

	@Column(name = "nick_name", length = 120)
	private String nickName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	@Builder.Default
	private UserStatus status = UserStatus.PENDING_VERIFICATION;

	@Column(name = "email_verified", nullable = false)
	@Builder.Default
	private Boolean emailVerified = false;

}
