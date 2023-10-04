package ru.otus.spring.hw13.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.spring.hw13.dto.CommentDto;
import ru.otus.spring.hw13.entity.Comment;

import java.util.Set;

@Mapper(config = LibraryMapperConfig.class)
public interface CommentMapper {

    @Mapping(target = "bookId", source = "book.id")
    CommentDto toDto(Comment comment);

    Comment toEntity(CommentDto commentDto);

    Set<CommentDto> toDto(Set<Comment> comments);

    Set<Comment> toEntity(Set<CommentDto> comments);

}
