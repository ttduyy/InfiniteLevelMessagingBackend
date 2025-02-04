package com.example.infinite_level_messaging_system.Services;

import com.example.infinite_level_messaging_system.DTO.LoginDTO;
import com.example.infinite_level_messaging_system.DTO.UserDTO;
import com.example.infinite_level_messaging_system.Entity.User;
import com.example.infinite_level_messaging_system.Response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    LoginResponse login(LoginDTO loginDTO);

    User registration(UserDTO userDTO);

    User getUserLoggedIn();

    User checkLogin(HttpServletRequest request);
}
