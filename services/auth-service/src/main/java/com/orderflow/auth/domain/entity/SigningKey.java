package com.orderflow.auth.domain.entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.orderflow.auth.enums.KeyStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "signing_keys")
public class SigningKey extends AuditModel {

	@Id
	private String kid;

	@Column(name = "public_key", nullable = false, columnDefinition = "TEXT")
	private String publicKey;

	@Column(name = "private_key", nullable = false, columnDefinition = "TEXT")
	private String privateKey;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private KeyStatus status;

	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@PrePersist
	public void onPreInsert() {
		setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
		onPreUpdate();
	}

	@PreUpdate
	public void onPreUpdate() {
		setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
	}
}
