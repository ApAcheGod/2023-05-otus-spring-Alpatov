package ru.otus.spring.hw17.service;

import ru.otus.spring.hw17.entity.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorService {

    Optional<Author> findById(UUID id);

    List<Author> findAll();

    Author save(Author author);

    void deleteById(UUID id);

    Optional<Author> findByNameAndLasName(String name, String lastName);

}
