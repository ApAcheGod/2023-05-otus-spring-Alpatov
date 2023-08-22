package ru.otus.spring.hw7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw7.entity.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    Optional<Author> findById(UUID id);

    List<Author> findAll();

    Optional<Author> findByNameAndLastName(String name, String lastName);

}
