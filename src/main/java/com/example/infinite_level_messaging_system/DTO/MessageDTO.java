package com.example.infinite_level_messaging_system.DTO;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageDTO {

    private String id;

    private String username;

    @Size(min = 3, max = 200, message = "Message must be between 3 and 200 characters.")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\p{P}\\p{Zs}]*$", message = "Message can only contain letters, numbers, punctuation, and spaces.")
    private String content;

    private LocalDateTime createAt;

}
