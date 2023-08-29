package ru.otus.spring.hw8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw8.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, Long> {

    Optional<Genre> findByName(String name);
}
