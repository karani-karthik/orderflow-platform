package com.orderflow.inventory.domain.entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory")
public class Inventory {

	@Id
	@Column(name = "product_id")
	private UUID productId;

	@Column(name = "available_qty", nullable = false)
	private Integer availableQty;

	@Column(name = "reserved_qty", nullable = false)
	private Integer reservedQty;

	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onPrePersist() {
		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	protected void onPreUpdate() {
		this.updatedAt = LocalDateTime.now(ZoneOffset.UTC);
	}

}
