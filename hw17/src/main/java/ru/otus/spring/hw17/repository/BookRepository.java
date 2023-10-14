package ru.otus.spring.hw17.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw17.entity.Book;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    Optional<Book> findByTitle(String title);

}
