package com.example.infinite_level_messaging_system.Services;

import com.example.infinite_level_messaging_system.DTO.MessageDTO;
import com.example.infinite_level_messaging_system.Entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MessageService {
    List<Message> getAll();

    List<Message> getMessageByUsername(String username);

    Message saveMessage(MessageDTO messageDTO);
}
