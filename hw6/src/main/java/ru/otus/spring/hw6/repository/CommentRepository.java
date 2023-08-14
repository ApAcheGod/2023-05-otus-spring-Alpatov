package ru.otus.spring.hw6.repository;

import ru.otus.spring.hw6.entity.Book;
import ru.otus.spring.hw6.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository {

    Optional<Comment> findById(UUID id);

    List<Comment> findAll();

    void insert(Comment comment);

    void update(Comment comment);

    void save(Comment comment);

    void deleteById(UUID id);

    List<Comment> findAllByBook(Book book);
}
