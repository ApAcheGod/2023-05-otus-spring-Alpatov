package ru.otus.spring.hw9.service;

import ru.otus.spring.hw9.dto.CommentDto;
import ru.otus.spring.hw9.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentService {

    Optional<Comment> findById(UUID id);

    List<Comment> findAll();

    Comment save(Comment comment);

    Comment save(CommentDto commentDto);

    void deleteById(UUID id);
}
