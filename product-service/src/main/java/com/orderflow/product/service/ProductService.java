package com.orderflow.product.service;

import java.util.List;
import java.util.UUID;

import com.orderflow.product.dto.request.CreateProductRequest;
import com.orderflow.product.dto.response.ProductResponse;

public interface ProductService {

	ProductResponse create(CreateProductRequest request);

	ProductResponse findById(UUID id);

	List<ProductResponse> findByIds(List<UUID> ids);

	void delete(UUID id);

}
