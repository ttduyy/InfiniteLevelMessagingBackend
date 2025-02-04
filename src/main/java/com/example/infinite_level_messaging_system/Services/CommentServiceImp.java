package com.example.infinite_level_messaging_system.Services;

import com.example.infinite_level_messaging_system.Entity.Comment;
import com.example.infinite_level_messaging_system.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService{

    private final CommentRepository commentRepository;
    @Override
    public List<Comment> getCommentOfMessage(String messageId) {

        return commentRepository.findBy;
    }

    @Override
    public List<Comment> getCommentOfParentComment(String messageId) {
        return null;
    }
}
