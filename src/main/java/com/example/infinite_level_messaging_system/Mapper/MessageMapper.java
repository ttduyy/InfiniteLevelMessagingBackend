package com.example.infinite_level_messaging_system.Mapper;

import com.example.infinite_level_messaging_system.Entity.Message;
import com.example.infinite_level_messaging_system.DTO.MessageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "createAt", target = "createAt")
    MessageDTO messageToMessageDTO(Message message);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "createAt", target = "createAt")
    Message messageDTOToMessage(MessageDTO messageDTO);
}
