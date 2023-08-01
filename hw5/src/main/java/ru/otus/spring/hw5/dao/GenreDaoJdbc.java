package ru.otus.spring.hw5.dao;

import ru.otus.spring.hw5.domain.Genre;

import java.util.List;
import java.util.UUID;

public interface GenreDaoJdbc {

    Genre findById(UUID id);

    List<Genre> findAll();

    UUID insert(Genre genre);

    void update(Genre genre);

    void deleteById(UUID id);

    Genre findByName(String name);
}
