package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    List<UserEntity> getUserByAll();

    UserDto getUserDetailsByEmail(String email);
}
