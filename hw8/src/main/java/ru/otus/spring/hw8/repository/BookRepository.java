package ru.otus.spring.hw8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw8.domain.Author;
import ru.otus.spring.hw8.domain.Book;
import ru.otus.spring.hw8.domain.Genre;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, Long> {

    Boolean existsByGenresContains(Genre genre);

    Boolean existsByAuthorsContains(Author author);

    Optional<Book> findByTitle(String title);
}
