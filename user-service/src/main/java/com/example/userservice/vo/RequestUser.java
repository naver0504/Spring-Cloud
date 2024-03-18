package com.example.userservice.vo;

import lombok.Data;

@Data
public class RequestUser {

    private String email;
    private String password;
    private String name;
}
