package com.example.infinite_level_messaging_system.Entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document("messages")
public class Message {

    @Id
    private String id;

    private String username; //username of poster
    private String content;
    private LocalDateTime createAt;

}

