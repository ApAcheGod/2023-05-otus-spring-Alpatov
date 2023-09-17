package ru.otus.spring.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw11.domain.Author;
import ru.otus.spring.hw11.domain.Book;
import ru.otus.spring.hw11.domain.Genre;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Boolean existsByGenresContains(Genre genre);

    Boolean existsByAuthorsContains(Author author);

    Mono<Book> findByTitle(String title);
}
