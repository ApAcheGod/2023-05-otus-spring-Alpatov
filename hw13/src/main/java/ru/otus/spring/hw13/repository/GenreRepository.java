package ru.otus.spring.hw13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw13.entity.Genre;

import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {

    Optional<Genre> findByName(String name);
}