package com.orderflow.product.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderflow.product.domain.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	List<Product> findByIdIn(Collection<String> ids);

}
