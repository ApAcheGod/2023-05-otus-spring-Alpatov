package ru.otus.spring.hw18.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw18.entity.Author;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    Optional<Author> findByNameAndLastName(String name, String lastName);

}
