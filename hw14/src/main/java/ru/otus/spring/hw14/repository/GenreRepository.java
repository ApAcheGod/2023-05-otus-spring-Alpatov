package ru.otus.spring.hw14.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw14.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
