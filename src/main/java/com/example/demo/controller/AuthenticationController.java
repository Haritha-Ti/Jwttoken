package com.example.demo.controller;

import javax.security.sasl.AuthenticationException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.dto.UserDto;
import com.example.demo.model.AuthToken;
import com.example.demo.model.LoginUser;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    //noobs will always make mistakes, soon they will realize how things work
    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody LoginUser loginUser) throws AuthenticationException {
    	Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
		/*
		 * System.out.println("username "+loginUser.getUsername()+" "+loginUser.
		 * getPassword()); final Authentication authentication =
		 * authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
		 * loginUser.getUsername(), loginUser.getPassword() ) );
		 */
        
        //System.out.println("auth  : "+authentication);
        //SecurityContextHolder.getContext().setAuthentication(authentication);
        //final UserDto user = userService.findOne(loginUser.getUsername());
       // System.out.println(user.getUsername()+user.getPassword());
    	UserDto user = new UserDto();
    	user.setUsername(auth.getName());
        final String token = jwtTokenUtil.generateToken(user);
        //System.out.println("token "+token);
        return ResponseEntity.ok(new AuthToken(token));
    }

}

