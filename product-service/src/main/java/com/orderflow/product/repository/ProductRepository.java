package com.orderflow.product.repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderflow.product.domain.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

	List<Product> findByIdIn(Collection<UUID> ids);

}
