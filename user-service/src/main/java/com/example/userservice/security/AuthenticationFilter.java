package com.example.userservice.security;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final Environment environment;
    private final SecretKey secretKey;

    public AuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, UserService userService, Environment environment) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.environment = environment;
        this.secretKey = Keys.hmacShaKeyFor(environment.getProperty("token.secret").getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {



        try(InputStream inputStream = request.getInputStream()) {
            final RequestLogin requestLogin = objectMapper.readValue(inputStream, RequestLogin.class);
            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(requestLogin.getEmail(), requestLogin.getPwd(), new ArrayList<>());
            return getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        final String email = ((User)authResult.getPrincipal()).getUsername();
        UserDto userDetails = userService.getUserDetailsByEmail(email);

        String jwtToken = Jwts.builder()
                .setSubject(userDetails.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(secretKey)
                .compact();


        response.addHeader("token", jwtToken);
        response.addHeader("userId", userDetails.getUserId());


    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
