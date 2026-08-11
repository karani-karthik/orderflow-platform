package com.orderflow.product.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderflow.product.domain.entity.Product;
import com.orderflow.product.dto.request.CreateProductRequest;
import com.orderflow.product.dto.request.UpdateProductRequest;
import com.orderflow.product.dto.response.ProductResponse;
import com.orderflow.product.repository.ProductRepository;
import com.orderflow.product.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	@Transactional
	public ProductResponse create(CreateProductRequest req) {
		Product p = Product.builder().name(req.name()).description(req.description()).price(req.price())
				.currency(req.currency()).imageUrl(req.imageUrl()).category(req.category()).build();

		productRepository.save(p);
		return ProductResponse.from(p);
	}

	@Override
	@Transactional(readOnly = true)
	public ProductResponse findById(UUID id) {
		return productRepository.findById(id).map(ProductResponse::from).orElseThrow();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductResponse> findByIds(List<UUID> ids) {
		return productRepository.findByIdIn(ids).stream().map(ProductResponse::from).toList();
	}

	@Transactional
	public ProductResponse update(UUID id, UpdateProductRequest req) {
		Product product = productRepository.findById(id).orElseThrow();

		productRepository.save(product);
		return ProductResponse.from(product);
	}

	@Transactional
	public void delete(UUID id) {
		if (!productRepository.existsById(id)) {

		}
		productRepository.deleteById(id);
	}
}
