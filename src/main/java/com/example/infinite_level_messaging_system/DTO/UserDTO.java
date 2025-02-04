package com.example.infinite_level_messaging_system.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserDTO {

    private String id;

    private String username;

    private String password;

    private String confirmPassword;

    private String email;

    private String phone;

    private String avatar;

    private LocalDate birthday;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
