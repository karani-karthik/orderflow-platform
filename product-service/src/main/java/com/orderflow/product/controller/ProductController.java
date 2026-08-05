package com.orderflow.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderflow.product.dto.request.CreateProductRequest;
import com.orderflow.product.dto.response.ApiResponse;
import com.orderflow.product.dto.response.ProductResponse;
import com.orderflow.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	public ResponseEntity<ApiResponse<ProductResponse>> create(@RequestBody CreateProductRequest request) {
		return ResponseEntity.ok(ApiResponse.ok(productService.create(request)));
	}
}
