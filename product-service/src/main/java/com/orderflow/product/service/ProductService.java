package com.orderflow.product.service;

import com.orderflow.product.dto.request.CreateProductRequest;
import com.orderflow.product.dto.response.ProductResponse;

public interface ProductService {

	ProductResponse create(CreateProductRequest request);

}
