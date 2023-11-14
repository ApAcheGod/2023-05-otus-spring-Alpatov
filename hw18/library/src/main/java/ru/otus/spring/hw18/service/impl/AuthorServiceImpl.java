package ru.otus.spring.hw18.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw18.entity.Author;
import ru.otus.spring.hw18.repository.AuthorRepository;
import ru.otus.spring.hw18.service.AuthorService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
    }

    @Override
    @CircuitBreaker(name = "authorServiceCircuitBreaker")
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteById(UUID id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Optional<Author> findByNameAndLasName(String name, String lastName) {
        return authorRepository.findByNameAndLastName(name, lastName);
    }
}
