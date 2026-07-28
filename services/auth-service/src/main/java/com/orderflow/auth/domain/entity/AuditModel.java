package com.orderflow.auth.domain.entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class AuditModel extends BaseModel {

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
