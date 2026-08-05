package com.orderflow.auth.exception;

public class RoleNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RoleNotFoundException(String roleName) {
		super("error.role.not.found");
	}

}
