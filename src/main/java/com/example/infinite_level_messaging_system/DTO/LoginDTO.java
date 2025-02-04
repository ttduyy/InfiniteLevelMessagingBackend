package com.example.infinite_level_messaging_system.DTO;

import lombok.Data;

@Data
public class LoginDTO {

    private String username;

    private String password;

    private boolean rememberMe;

}
