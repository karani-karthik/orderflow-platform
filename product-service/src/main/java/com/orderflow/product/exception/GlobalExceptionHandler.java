package com.orderflow.product.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.orderflow.product.dto.response.ApiResponse;
import com.orderflow.product.il8n.MessageHelper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final MessageHelper messageHelper;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		log.warn("Validation failed: {}", ex.getMessage());

		// Map Spring's FieldError to your custom ApiResponse.FieldError
		List<ApiResponse.FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
				.map(this::mapFieldError).collect(Collectors.toList());

		ApiResponse<Void> response = ApiResponse.error(messageHelper.getMessage("validation.error"), fieldErrors);
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleEntityNotFound(EntityNotFoundException ex) {
		log.warn("Entity not found: {}", ex.getMessage());

		ApiResponse<Void> response = ApiResponse.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse<Void>> handleMessageNotReadable(HttpMessageNotReadableException ex) {
		log.warn("Malformed JSON request: {}", ex.getMessage());

		ApiResponse<Void> response = ApiResponse.error("request.invalid.format");
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
		log.error("Unexpected error occurred: ", ex);

		ApiResponse<Void> response = ApiResponse.error("internal.server.error");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	private ApiResponse.FieldError mapFieldError(FieldError springError) {
		return ApiResponse.FieldError.builder().field(springError.getField()).message(springError.getDefaultMessage())
				.build();
	}
}
