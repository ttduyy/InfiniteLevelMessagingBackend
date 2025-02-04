package com.example.infinite_level_messaging_system.Response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiResponse<T> {
    private T data;
    private String message;
    private int status;
    private boolean success;
    private T error;
}