package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

public interface UserService {
	
	

	List findAll();

	User findOne(Long id);

	User save(UserDto user);

	UserDto findOne(String username);
	
}
