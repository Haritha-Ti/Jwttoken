package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Component("userService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

	@Autowired
	UserRepository userRepository;

	
	
	@Override
	public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loading user by username 4 "+username);
		User userdetails=userRepository.getUser(username);
		System.out.println("userdetails 4: "+userdetails.getPassword()+" : "+userdetails.getUsername());
		UserDto userdto=new UserDto();
		userdto.setUsername(userdetails.getUsername());
		userdto.setPassword(userdetails.getPassword());
		userdto.setAge(userdetails.getAge());
		userdto.setSalary(userdetails.getSalary());
		return userdto;
	}

}
