package ru.otus.spring.hw17.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw17.entity.Genre;

import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {

    Optional<Genre> findByName(String name);
}
