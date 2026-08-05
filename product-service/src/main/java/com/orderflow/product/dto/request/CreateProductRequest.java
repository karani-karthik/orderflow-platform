package com.orderflow.product.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(

		@NotBlank @Size(max = 200) String name,

		String description,

		@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal price,

		@NotBlank @Size(min = 3, max = 3) String currency,

		@NotBlank @Size(max = 64) String category,

		@Size(max = 500) String imageUrl

) {
}
