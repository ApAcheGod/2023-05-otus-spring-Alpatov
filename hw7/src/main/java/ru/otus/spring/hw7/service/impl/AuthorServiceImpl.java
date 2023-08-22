package ru.otus.spring.hw7.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw7.entity.Author;
import ru.otus.spring.hw7.repository.AuthorRepository;
import ru.otus.spring.hw7.service.AuthorService;

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
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Optional<Author> findByNameAndLasName(String name, String lastName) {
        return authorRepository.findByNameAndLastName(name, lastName);
    }
}
