package ru.otus.spring.hw18.service;

import ru.otus.spring.hw18.entity.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {

    Optional<Book> findById(UUID id);

    List<Book> findAll();

    Book save(Book book);

    void deleteById(UUID id);

    Optional<Book> findByTitle(String title);
}
