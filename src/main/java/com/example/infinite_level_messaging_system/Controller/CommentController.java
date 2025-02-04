package com.example.infinite_level_messaging_system.Controller;

import com.example.infinite_level_messaging_system.Entity.Comment;
import com.example.infinite_level_messaging_system.Exceptions.BadRequestException;
import com.example.infinite_level_messaging_system.Response.ApiResponse;
import com.example.infinite_level_messaging_system.Services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{messageId}")
    public ResponseEntity<?> getCommentOfMessage(@PathVariable String messageId){
        try {
            List<Comment> comments = commentService.getCommentOfMessage(messageId);
            return ResponseEntity.ok(ApiResponse
                    .builder()
                            .status(200)
                            .message("Get comment success")
                            .data(comments)
                            .success(true)
                    .build()
            );

        }catch (BadRequestException exception){
            return ResponseEntity.ok(ApiResponse
                    .builder()
                    .status(400)
                    .message("Get comment fail")
                    .error(exception.getMessage())
                    .success(false)
                    .build()
            );
        }
    }
}
