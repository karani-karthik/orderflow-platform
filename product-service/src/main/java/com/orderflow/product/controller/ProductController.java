package com.orderflow.product.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderflow.product.dto.request.CreateProductRequest;
import com.orderflow.product.dto.response.ProductResponse;
import com.orderflow.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductResponse> create(@RequestBody CreateProductRequest request) {
		return ResponseEntity.ok(productService.create(request));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> findById(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.ok(productService.findById(id));
	}

	@PostMapping("/bulk")
	public ResponseEntity<List<ProductResponse>> findByIds(@RequestBody List<UUID> ids) {
		return ResponseEntity.ok(productService.findByIds(ids));
	}
	
}
