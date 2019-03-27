package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDto;
import com.example.demo.repository.UserRepository;

@Component("userService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDto userdetails=userRepository.getUser(username);
		
		return userdetails;
	}

}
