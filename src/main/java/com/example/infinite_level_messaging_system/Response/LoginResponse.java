package com.example.infinite_level_messaging_system.Response;

import com.example.infinite_level_messaging_system.DTO.UserDTO;
import com.example.infinite_level_messaging_system.Entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("user")
    private UserDTO user;
    private String phone;
}
