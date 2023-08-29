package ru.otus.spring.hw8.service;

import ru.otus.spring.hw8.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> findById(Long id);

    List<Author> findAll();

    void save(Author author);

    void deleteById(Long id);

    Optional<Author> findByNameAndLasName(String name, String lastName);
}
