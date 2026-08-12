package com.orderflow.product.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.orderflow.product.domain.entity.Product;
import com.orderflow.product.domain.enums.ProductStatus;

public final class ProductSpecifications {

	private ProductSpecifications() {
	}

	public static Specification<Product> nameContains(String name) {
		return (root, query, cb) -> name == null || name.isBlank() ? null
				: cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
	}

	public static Specification<Product> hasCategory(String category) {
		return (root, query, cb) -> category == null || category.isBlank() ? null
				: cb.equal(root.get("category"), category);
	}

	public static Specification<Product> priceBetween(BigDecimal min, BigDecimal max) {
		return (root, q, cb) -> {
			if (min == null && max == null) {
				return null;
			}
			if (min == null) {
				return cb.lessThanOrEqualTo(root.get("price"), max);
			}
			if (max == null) {
				return cb.greaterThanOrEqualTo(root.get("price"), min);
			}
			return cb.between(root.get("price"), min, max);
		};
	}

	public static Specification<Product> hasStatus(ProductStatus status) {
		return (root, q, cb) -> status == null ? null : cb.equal(root.get("status"), status);
	}
}
