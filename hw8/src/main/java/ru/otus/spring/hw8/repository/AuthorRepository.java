package ru.otus.spring.hw8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw8.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, Long> {

    Optional<Author> findByNameAndLastName(String name, String lastName);
}
