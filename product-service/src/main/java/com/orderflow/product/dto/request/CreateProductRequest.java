package com.orderflow.product.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(

		@NotBlank(message = "{product.name.notblank}") 
		@Size(max = 200, message = "{product.name.size}") 
		String name,

		String description,

		@NotNull(message = "{product.price.notnull}")
		@DecimalMin(value = "0.0", inclusive = false, message = "{product.price.decimalmin}")
		BigDecimal price,

		@NotBlank(message = "{product.currency.notblank}")
		@Size(min = 3, max = 3, message = "{product.currency.size}") 
		String currency,

		@NotBlank(message = "{product.category.notblank}")
		@Size(max = 64, message = "{product.category.size}") 
		String category,

		@Size(max = 500, message = "{product.imageUrl.size}") 
		String imageUrl

) {
}
