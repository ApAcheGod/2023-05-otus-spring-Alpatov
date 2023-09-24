package ru.otus.spring.hw14.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw14.domain.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}
