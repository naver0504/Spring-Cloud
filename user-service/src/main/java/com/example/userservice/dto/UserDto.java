package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {

    private String email;
    private String pwd;
    private String name;
    private String userId;
    private String encryptedPwd;
    private LocalDateTime createdAt;

    private List<ResponseOrder> orders;


}
