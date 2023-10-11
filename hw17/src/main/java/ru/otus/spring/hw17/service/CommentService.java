package ru.otus.spring.hw17.service;

import ru.otus.spring.hw17.dto.CommentDto;
import ru.otus.spring.hw17.entity.Comment;

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
