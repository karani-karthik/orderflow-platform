package com.orderflow.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderflow.product.domain.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}
