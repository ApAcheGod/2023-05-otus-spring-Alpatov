package ru.otus.spring.hw7.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw7.entity.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    @EntityGraph("book-authors-genres-entity-graph")
    Optional<Book> findById(UUID id);

    @EntityGraph("book-authors-genres-entity-graph")
    List<Book> findAll();

    Optional<Book> findByTitle(String title);

}
