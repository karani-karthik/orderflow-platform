package com.orderflow.auth.service;

import com.orderflow.auth.domain.dto.request.UserRequest;
import com.orderflow.auth.domain.dto.response.UserResponse;

public interface UserService {

	UserResponse createUser(UserRequest request);

	UserResponse getUser(String id);

}
