package ru.otus.spring.hw14.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw14.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
