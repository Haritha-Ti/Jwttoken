package com.example.demo.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.demo.model.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.Driver;
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		String message = "Sorry, You're not authorized to access this resource.";
		ObjectMapper mapper = new ObjectMapper();

		String header = request.getHeader(Constants.HEADER_STRING);
		if (header == null || !header.startsWith(Constants.TOKEN_PREFIX)) {
			message = "couldn't find bearer string, will ignore the header";
		}

		response.setStatus(HttpServletResponse.SC_OK);
//		StatusResponse exceptionResponse = new StatusResponse<>(500,407,"FAIL",message);
//		response.getOutputStream().print(mapper.writeValueAsString(exceptionResponse));
	}

}
