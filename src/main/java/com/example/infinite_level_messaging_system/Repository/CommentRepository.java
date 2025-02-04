package com.example.infinite_level_messaging_system.Repository;

import com.example.infinite_level_messaging_system.Entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
}
