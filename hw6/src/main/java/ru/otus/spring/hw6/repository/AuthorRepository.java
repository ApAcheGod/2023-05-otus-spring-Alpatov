package ru.otus.spring.hw6.repository;

import ru.otus.spring.hw6.dto.AuthorDto;
import ru.otus.spring.hw6.entity.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository {

    Optional<Author> findById(UUID id);

    Optional<AuthorDto> findDtoById(UUID id); // Не придумал нормальное название для ДТО

    List<Author> findAll();

    List<AuthorDto> findAllDto();

    void insert(Author author);

    void update(Author author);

    void save(Author author);

    void deleteById(UUID id);

    Optional<Author> findByNameAndLasName(String name, String lastName);

    Optional<AuthorDto> findDtoByNameAndLasName(String name, String lastName);

}
