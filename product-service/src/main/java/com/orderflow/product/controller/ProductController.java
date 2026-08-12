package com.orderflow.product.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderflow.product.dto.request.CreateProductRequest;
import com.orderflow.product.dto.response.ApiResponse;
import com.orderflow.product.dto.response.ProductResponse;
import com.orderflow.product.il8n.MessageHelper;
import com.orderflow.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	private final MessageHelper messageHelper;

	@PostMapping
	public ResponseEntity<ApiResponse<ProductResponse>> create(@RequestBody @Valid CreateProductRequest request) {
		ProductResponse response = productService.create(request);
		return ResponseEntity.ok(ApiResponse.ok(messageHelper.getMessage("product.create.success", response.id()), response));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductResponse>> findById(@PathVariable(name = "id") UUID id) {
		ProductResponse response = productService.findById(id);
		return ResponseEntity.ok(ApiResponse.ok(messageHelper.getMessage("product.retrieve.success", id), response));
	}

	@PostMapping("/bulk")
	public ResponseEntity<ApiResponse<List<ProductResponse>>> findByIds(@RequestBody List<UUID> ids) {
		List<ProductResponse> responses = productService.findByIds(ids);
		return ResponseEntity.ok(ApiResponse.ok(messageHelper.getMessage("product.retrieve.list.success", ids.size()), responses));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID id) {
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
