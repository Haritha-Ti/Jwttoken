package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.dto.UserDto;
import com.example.demo.model.LoginUser;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	JwtTokenUtil jwtToken;

//    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public List listUser() {
		return userService.findAll();
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public User getOne(@PathVariable(value = "id") Long id) {
		return userService.findOne(id);
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public User saveUser(@RequestBody UserDto user) {
		System.out.println("user " + user.getUsername() + " pass " + user.getPassword() + " age " + user.getAge()
				+ "  salary " + user.getSalary());
		return userService.save(user);
	}

	@PostMapping(value = "/getLoginCredentials")
	@ResponseBody
	public String adminLogin(@RequestBody LoginUser requestdata) {

		// getting string value from json request
		String username = requestdata.getUsername();
		String password = requestdata.getPassword();
		System.out.println("username " + username + "password " + password);
		try {

			if ((username != null) && (username.length() > 0) && (!username.equals(" ")) && (password != null)
					&& (password.length() > 0)) {

				// Invoking user authentication method
				User usercheck = userService.login_authentication(username, password);
				
				String token = null;
				if (usercheck != null) {
					UserDto userdata = new UserDto();
					userdata.setUsername(usercheck.getUsername());
					userdata.setPassword(usercheck.getUsername());
					token = jwtToken.generateToken(userdata);
					System.out.println("token : " + token);
				}
				return "token : "+token;
			} else {

				return null;
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);

			return null;
		}

	}

}
