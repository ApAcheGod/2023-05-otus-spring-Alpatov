package ru.otus.spring.hw8.service;

import ru.otus.spring.hw8.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Optional<Genre> findById(Long id);

    List<Genre> findAll();

    void save(Genre genre);

    void deleteById(Long id);

    Optional<Genre> findByName(String name);
}
