package com.luv2code.ecommerce.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
