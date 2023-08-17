package ru.otus.spring.hw6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw6.entity.Author;
import ru.otus.spring.hw6.repository.AuthorRepository;
import ru.otus.spring.hw6.service.AuthorService;

import java.util.List;
import java.util.Objects;
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
    public void insert(Author author) {
        if (Objects.nonNull(author.getId())) {
            throw new UnsupportedOperationException("Entity shouldn't contains ID");
        }
        authorRepository.insert(author);
    }

    @Override
    @Transactional
    public void update(Author author) {
        if (Objects.isNull(author.getId())) {
            throw new UnsupportedOperationException("Entity should contains ID");
        }
        authorRepository.update(author);
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
        return authorRepository.findByNameAndLasName(name, lastName);
    }
}
