package com.example.infinite_level_messaging_system.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDTO {

    private String id;

    private String username;

    private String messageId;

    private String parentId;

    private String content;

    private LocalDateTime createdAt;

}
