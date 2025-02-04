package com.example.infinite_level_messaging_system.Services;

import com.example.infinite_level_messaging_system.DTO.MessageDTO;
import com.example.infinite_level_messaging_system.Entity.Message;
import com.example.infinite_level_messaging_system.Entity.User;
import com.example.infinite_level_messaging_system.Exceptions.BadRequestException;
import com.example.infinite_level_messaging_system.Exceptions.InternalServerException;
import com.example.infinite_level_messaging_system.Mapper.MessageMapper;
import com.example.infinite_level_messaging_system.Repository.MessageRepository;
import com.example.infinite_level_messaging_system.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImp implements MessageService{
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public List<Message> getAll() {
        return messageRepository.findAll(Sort.by(Sort.Direction.DESC,"createAt"));
    }

    @Override
    public List<Message> getMessageByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow( () -> new BadRequestException("Username is not exist"));

            return messageRepository.findByUsername(
                    user.getUsername(),
                    Sort.by(Sort.Direction.DESC,"createAt") //lasted message first
            );

        }catch (BadRequestException exception){
            throw new BadRequestException(exception.getMessage());
        }
    }

    @Override
    public Message saveMessage(MessageDTO messageDTO) {
        try {
            return messageRepository.save(
                    Message
                            .builder()
                            .username(messageDTO.getUsername())
                            .content(messageDTO.getContent())
                            .createAt(LocalDateTime.now())
                            .build());
        }catch (InternalServerException exception){
            throw new InternalServerException("Something was wrong in server, please try later");
        }
    }
}
