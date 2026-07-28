package com.orderflow.auth.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.orderflow.auth.domain.dto.response.ApiResponse;
import com.orderflow.auth.exception.i18n.MessageHelper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final MessageHelper messageHelper;

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Void>> handleEmailExists(EmailAlreadyExistsException ex,
			HttpServletRequest request) {
		String localizedMessage = messageHelper.getMessage("error.email.exists");
		ApiResponse<Void> response = ApiResponse.error(localizedMessage, HttpStatus.CONFLICT, request.getRequestURI(),
				null);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		Map<String, Object> errors = ex.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(fieldError -> fieldError.getField(), fieldError -> {
					return messageHelper.getMessage(fieldError.getDefaultMessage(), fieldError.getArguments());
				}, (existing, replacement) -> existing + "; " + replacement));

		String localizedMessage = messageHelper.getMessage("error.validation.failed");

		ApiResponse<Void> response = ApiResponse.error(localizedMessage, HttpStatus.BAD_REQUEST,
				request.getRequestURI(), errors);
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
		String localizedMessage = messageHelper.getMessage("error.user.not.found");
		ApiResponse<Void> response = ApiResponse.error(localizedMessage, HttpStatus.NOT_FOUND, request.getRequestURI(),
				null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleRoleNotFound(RoleNotFoundException ex, HttpServletRequest request) {
		String localizedMessage = messageHelper.getMessage("error.role.not.found");
		ApiResponse<Void> response = ApiResponse.error(localizedMessage, HttpStatus.INTERNAL_SERVER_ERROR,
				request.getRequestURI(), null);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex, HttpServletRequest request) {
		String localizedMessage = messageHelper.getMessage("error.generic");
		ApiResponse<Void> response = ApiResponse.error(localizedMessage, HttpStatus.INTERNAL_SERVER_ERROR,
				request.getRequestURI(), null);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
