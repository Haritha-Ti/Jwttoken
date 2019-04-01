package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

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
		String newdata=bCryptPasswordEncoder.encode(user.getPassword());
		System.out.println("newdata 3: "+newdata);
		userdata.setPassword(newdata);
		User user1 = userRepository.save(userdata);
		System.out.println(user1.getUsername() + " " + user1.getPassword());
		return user1;
	}

	@Override
	public UserDto findOne(String username) {
		System.out.println("username frm reqst 3: "+username);
		User user = userRepository.getUser(username);
		System.out.println("user details retured 3:"+user.getUsername()+" "+user.getPassword()+" "+user.getSalary());
		UserDto userdto=new UserDto();
		userdto.setUsername(user.getUsername());
		userdto.setPassword(user.getPassword());
		userdto.setAge(user.getAge());
		userdto.setSalary(user.getSalary());
		return userdto;
	}

	@Override
	public User login_authentication(String username, String password) {
			User checkuser = null;
		
		try {
//			String pass=bCryptPasswordEncoder.encode(password);
//			System.out.println("pass  "+pass);
//			calling sql query by passing parameters			
			checkuser = userRepository.getUserdetails(username);
			boolean passencrpt=bCryptPasswordEncoder.matches(password, checkuser.getPassword());
			System.out.println("boolean val after login auth 3:"+passencrpt);
			if(passencrpt)
				return checkuser;
			else
				return null;
		} catch (Exception e) {
			System.out.println("Exception : " + e);
			return checkuser;
		}
	}

}
