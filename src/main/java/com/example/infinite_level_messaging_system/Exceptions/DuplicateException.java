package com.example.infinite_level_messaging_system.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateException extends RuntimeException{
    public DuplicateException(String message) {
        super(message);
    }
}
