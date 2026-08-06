package com.orderflow.product.dto.request;

import java.math.BigDecimal;

import com.orderflow.product.domain.enums.ProductStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public record UpdateProductRequest(
        @Size(max = 200) String name,
        @Size(max = 2000) String description,
        @DecimalMin(value = "0.0", inclusive = false) BigDecimal price,
        @Size(max = 64) String category,
        @Size(max = 500) String imageUrl,
        ProductStatus status
) {
}
