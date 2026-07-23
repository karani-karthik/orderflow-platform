package com.orderflow.auth.service.impl;

import org.springframework.stereotype.Service;

import com.orderflow.auth.repository.UserRepository;
import com.orderflow.auth.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
    private final UserRepository userRepository;
    
}
