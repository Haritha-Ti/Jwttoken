package com.example.demo.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

import static com.example.demo.model.Constants.HEADER_STRING;
import static com.example.demo.model.Constants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("Jwt authentication filter start 2 : " + req);
		String header = req.getHeader(HEADER_STRING);
		System.out.println("Http Header 2 : " + header);
		String username = null;
		String authToken = null;
		if (header != null && header.startsWith(TOKEN_PREFIX)) {
			authToken = header.replace(TOKEN_PREFIX, "");
			System.out.println("Header checking if token 2 :" + authToken);
			try {
				username = jwtTokenUtil.getUsernameFromToken(authToken);
				System.out.println("username frm token 2 : " + username);
			} catch (IllegalArgumentException e) {
				logger.error("an error occured during getting username from token", e);
			} catch (ExpiredJwtException e) {
				logger.warn("the token is expired and not valid anymore", e);
			} catch (SignatureException e) {
				logger.error("Authentication Failed. Username or Password not valid.");
			}
		} else {
			System.out.println("header checking else 2 ");
			logger.warn("couldn't find bearer string, will ignore the header");
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			System.out.println("username checking if 2 ");
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			System.out.println("userdetails get by username 2 :" + userDetails.getUsername() + " pass : "
					+ userDetails.getPassword());

			if (jwtTokenUtil.validateToken(authToken, userDetails)) {
				System.out.println("token validation start 2");
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
				System.out.println("token validation completed 2");
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				System.out.println("authenticated user details 2 :" + username);
				logger.info("authenticated user " + username + ", setting security context");
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else
				System.out.println("token validation failed 2");
		}
		System.out.println("filter request start");
		chain.doFilter(req, res);
		System.out.println("filter request end");
	}
}
