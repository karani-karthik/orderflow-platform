package com.orderflow.product.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderflow.product.domain.entity.Product;
import com.orderflow.product.dto.request.CreateProductRequest;
import com.orderflow.product.dto.request.ProductSearchRequest;
import com.orderflow.product.dto.request.UpdateProductRequest;
import com.orderflow.product.dto.response.ProductResponse;
import com.orderflow.product.il8n.MessageHelper;
import com.orderflow.product.repository.ProductRepository;
import com.orderflow.product.repository.ProductSpecifications;
import com.orderflow.product.service.ProductService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final MessageHelper messageHelper;

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
		return productRepository.findById(id).map(ProductResponse::from)
				.orElseThrow(() -> new EntityNotFoundException(messageHelper.getMessage("product.not.found", id)));
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductResponse> findByIds(List<UUID> ids) {
		return productRepository.findByIdIn(ids).stream().map(ProductResponse::from).toList();
	}
	
    @Transactional(readOnly = true)
	public Page<ProductResponse> search(ProductSearchRequest request, Pageable pageable) {
		Specification<Product> spec = Specification.allOf(
				ProductSpecifications.nameContains(request.keyword()),
				ProductSpecifications.hasCategory(request.category()),
				ProductSpecifications.priceBetween(request.minPrice(), request.maxPrice()),
				ProductSpecifications.hasStatus(request.status()));
		return productRepository.findAll(spec, pageable).map(ProductResponse::from);
	}

	@Transactional
	public ProductResponse update(UUID id, UpdateProductRequest req) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(messageHelper.getMessage("product.not.found", id)));

		productRepository.save(product);
		return ProductResponse.from(product);
	}

	@Override
	@Transactional
	public void delete(UUID id) {
		if (!productRepository.existsById(id)) {
			throw new EntityNotFoundException(messageHelper.getMessage("product.not.found", id));
		}
		
		productRepository.deleteById(id);
	}
}
