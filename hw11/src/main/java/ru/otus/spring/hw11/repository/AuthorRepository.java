package ru.otus.spring.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw11.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

    Mono<Author> findByNameAndLastName(String name, String lastName);
}
