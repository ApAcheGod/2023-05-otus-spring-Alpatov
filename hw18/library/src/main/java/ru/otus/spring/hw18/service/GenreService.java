package ru.otus.spring.hw18.service;

import ru.otus.spring.hw18.entity.Genre;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreService {

    Optional<Genre> findById(UUID id);

    List<Genre> findAll();

    Genre save(Genre genre);

    void deleteById(UUID id);

    Optional<Genre> findByName(String name);
}
