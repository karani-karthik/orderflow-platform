package com.orderflow.auth.domain.dto.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private boolean success;
	private String message;
	private T data;
	private Map<String, Object> errors;
	private String path;
	private int statusCode;
	private String status;
	private LocalDateTime timestamp;

	// Factory methods
    public static <T> ApiResponse<T> success(T data, String message, String path) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .path(path)
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }
    
    public static <T> ApiResponse<T> error(String message, HttpStatus status, String path, Map<String, Object> errors) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .errors(errors)
                .path(path)
                .statusCode(status.value())
                .status(status.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .build();
    }
	
}
