package com.orderflow.product.dto.request;

import java.math.BigDecimal;

import com.orderflow.product.domain.enums.ProductStatus;

public record ProductSearchRequest(
		
        String keyword,
        
        String category,
        
        BigDecimal minPrice,
        
        BigDecimal maxPrice,
        
        ProductStatus status
) {
}
