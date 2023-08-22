package ru.otus.spring.hw6.repository;

import ru.otus.spring.hw6.entity.Genre;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreRepository {

    Optional<Genre> findById(UUID id);

    List<Genre> findAll();

    void insert(Genre genre);

    void update(Genre genre);

    void save(Genre genre);

    void deleteById(UUID id);

    Optional<Genre> findByName(String name);
}
