package com.example.infinite_level_messaging_system.Services;

import com.example.infinite_level_messaging_system.Entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentOfMessage(String messageId);
}
