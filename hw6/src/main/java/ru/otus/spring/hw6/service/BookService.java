package ru.otus.spring.hw6.service;

import ru.otus.spring.hw6.entity.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {

    Optional<Book> findById(UUID id);

    List<Book> findAll();

    void insert(Book book);

    void update(Book book);

    void save(Book book);

    void deleteById(UUID id);

    Optional<Book> findByTitle(String title);
}
