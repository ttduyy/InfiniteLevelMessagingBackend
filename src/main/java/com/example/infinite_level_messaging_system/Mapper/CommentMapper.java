package com.example.infinite_level_messaging_system.Mapper;

import com.example.infinite_level_messaging_system.Entity.Comment;
import com.example.infinite_level_messaging_system.DTO.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "messageId", target = "messageId")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "parentId", target = "parentId")
    @Mapping(source = "createdAt", target = "createdAt")
    CommentDTO commentToCommentDTO(Comment comment);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "messageId", target = "messageId")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "parentId", target = "parentId")
    @Mapping(source = "createdAt", target = "createdAt")
    Comment commentDTOToComment(CommentDTO commentDTO);
}
