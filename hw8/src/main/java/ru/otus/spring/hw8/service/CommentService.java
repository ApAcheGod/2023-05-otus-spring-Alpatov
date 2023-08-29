package ru.otus.spring.hw8.service;

import ru.otus.spring.hw8.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> findById(Long id);

    List<Comment> findAll();

    void save(Comment comment);

    void deleteById(Long id);
}
