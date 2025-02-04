package com.example.infinite_level_messaging_system.Entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document("messages")
public class Comment {
    @Id
    private String id;

    private String content;
    private String messageId;
    private String username; // username of commenter
    private String parentId; // ID of parent comment
    private LocalDateTime createdAt;
}
