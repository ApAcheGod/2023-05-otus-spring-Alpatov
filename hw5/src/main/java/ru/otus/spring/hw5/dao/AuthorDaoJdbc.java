package ru.otus.spring.hw5.dao;

import ru.otus.spring.hw5.domain.Author;

import java.util.List;
import java.util.UUID;

public interface AuthorDaoJdbc {

    Author findById(UUID id);

    List<Author> findAll();

    UUID insert(Author author);

    void update(Author author);

    void deleteById(UUID id);

    Author findByNameAndLasName(String name, String lastName);

}
