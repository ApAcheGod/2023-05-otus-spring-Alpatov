package ru.otus.spring.hw8.service;

import ru.otus.spring.hw8.domain.Author;
import ru.otus.spring.hw8.domain.Book;
import ru.otus.spring.hw8.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> findById(Long id);

    List<Book> findAll();

    void save(Book book);

    void deleteById(Long id);

    Optional<Book> findByTitle(String title);

    Boolean existsByGenresContains(Genre genre);

    Boolean existsByAuthorsContains(Author authors);
}
