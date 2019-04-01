package com.example.demo.config;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.example.demo.model.Constants.SIGNING_KEY;
import static com.example.demo.model.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;

@Component
public class JwtTokenUtil implements Serializable {

    public String getUsernameFromToken(String token) {
    	System.out.println("username frm token 5");
        return getClaimFromToken(token, Claims::getSubject);
    }
    

    public Date getExpirationDateFromToken(String token) {
    	System.out.println("token expiration date frm 5: "+token);
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public  <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver) {
    	System.out.println("get clain frm tokeen 5 : "+token);
        final Claims claims = getAllClaimsFromToken(token);
        System.out.println("clam 5: "+claims);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
    	System.out.println("getting all claims 5");
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        System.out.println("expiration date 5"+expiration);
        return expiration.before(new Date());
    }

    public String generateToken(UserDto user) {
    	System.out.println("token generation 5 ");
        return doGenerateToken(user.getUsername());
    }

    private String doGenerateToken(String subject) {
    	System.out.println("started token generation 5");
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("EMS")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        System.out.println("token validation 5 ");
        return (
              username.equals(userDetails.getUsername())
                    && !isTokenExpired(token));
    }

}
