package com.orderflow.product.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private final boolean success;

	private final T data;

	private final LocalDateTime timestamp;

	public static <T> ApiResponse<T> ok(T data) {
		return ApiResponse.<T>builder().success(true).data(data).timestamp(LocalDateTime.now()).build();
	}
}
