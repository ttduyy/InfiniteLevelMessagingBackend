package com.example.infinite_level_messaging_system.Repository;

import com.example.infinite_level_messaging_system.Entity.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message,String> {
    List<Message> findByUsername(String username, Sort createAt);
}
