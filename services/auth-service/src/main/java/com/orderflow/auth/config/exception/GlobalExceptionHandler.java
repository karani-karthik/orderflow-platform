package com.orderflow.auth.config.exception;

import java.net.URI;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ProblemDetail handleEmailExists(EmailAlreadyExistsException ex) {
		ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
		pd.setType(URI.create("https://auth.orderflow.com/errors/email-exists"));
		pd.setTitle("Email Already Registered");
		return pd;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
		String errors = ex.getBindingResult().getFieldErrors().stream()
				.map(e -> e.getField() + ": " + e.getDefaultMessage()).collect(Collectors.joining(", "));
		
		ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors);
		pd.setType(URI.create("https://auth.orderflow.com/errors/validation"));
		pd.setTitle("Validation Failed");
		return pd;
	}

}
