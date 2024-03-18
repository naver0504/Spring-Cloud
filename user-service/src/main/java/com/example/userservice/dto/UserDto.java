package com.example.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private String email;
    private String pwd;
    private String name;
    private String userId;
    private String encryptedPwd;
    private LocalDateTime createdAt;


}
