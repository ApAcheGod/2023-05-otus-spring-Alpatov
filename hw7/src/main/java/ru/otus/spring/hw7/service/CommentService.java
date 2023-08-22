package ru.otus.spring.hw7.service;

import ru.otus.spring.hw7.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentService {

    Optional<Comment> findById(UUID id);

    List<Comment> findAll();

    void save(Comment comment);

    void deleteById(UUID id);
}
