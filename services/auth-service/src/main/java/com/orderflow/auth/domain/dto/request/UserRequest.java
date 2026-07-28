package com.orderflow.auth.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

		@NotBlank(message = "{user.email.required}")
		@Email(message = "{user.email.invalid}")
		String email,
		
		@NotBlank(message = "{user.password.required}")
		@Size(min = 8, max = 72, message = "{user.password.size}")
		String password,

		@NotBlank(message = "{user.fullname.required}")
		@Size(min = 2, max = 100, message = "{user.fullname.size}")
		String fullName
) {
}
