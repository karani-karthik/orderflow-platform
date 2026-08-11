package com.orderflow.product.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.orderflow.product.domain.entity.Product;
import com.orderflow.product.domain.enums.ProductStatus;

public record ProductResponse(
		UUID id, String name, String description, 
		BigDecimal price, String currency,
		String category, String imageUrl, 
		ProductStatus status, LocalDateTime createdAt, 
		LocalDateTime updatedAt) {

	public static ProductResponse from(Product p) {
		return new ProductResponse(
				p.getId(), p.getName(), p.getDescription(), 
				p.getPrice(), p.getCurrency(),
				p.getCategory(), p.getImageUrl(), 
				p.getStatus(), p.getCreatedAt(), p.getUpdatedAt());
	}

}
