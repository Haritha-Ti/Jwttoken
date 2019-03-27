package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public List findAll() {
		List<User> userlist = userRepository.findAll();
		return userlist;
	}

	@Override
	public User findOne(Long id) {
		User user = userRepository.getOne(id);
		return user;
	}

	@Override
	public User save(UserDto user) {
		User userdata = new User();
		userdata.setPassword(user.getPassword());
		userdata.setSalary(user.getSalary());
		userdata.setUsername(user.getUsername());
		userdata.setAge(user.getAge());
		
		User user1 = userRepository.save(userdata);
		System.out.println(user1.getUsername() + " " + user1.getPassword());
		return user1;
	}

	@Override
	public UserDto findOne(String username) {
		System.out.println("username "+username);
		UserDto user = userRepository.getUser(username);
		System.out.println("return "+user.getPassword());
		return user;
	}

}
