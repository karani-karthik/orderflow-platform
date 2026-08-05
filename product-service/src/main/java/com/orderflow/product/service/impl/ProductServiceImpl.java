package com.orderflow.product.service.impl;

import org.springframework.stereotype.Service;

import com.orderflow.product.domain.entity.Product;
import com.orderflow.product.dto.request.CreateProductRequest;
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
	public ProductResponse create(CreateProductRequest req) {
		Product p = Product.builder()
				.name(req.name())
				.description(req.description())
				.price(req.price())
				.currency(req.currency())
				.imageUrl(req.imageUrl())
				.build();
		
		productRepository.save(p);
		return ProductResponse.from(p);
	}
}
