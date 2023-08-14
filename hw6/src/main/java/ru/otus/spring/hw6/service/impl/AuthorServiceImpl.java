package ru.otus.spring.hw6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw6.dto.AuthorDto;
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

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AuthorDto> findDtoById(UUID id) {
        return authorRepository.findDtoById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> findAllDto() {
        return authorRepository.findAllDto();
    }

    @Override
    public void insert(Author author) {
        if (Objects.nonNull(author.getId())) {
            throw new UnsupportedOperationException("Entity shouldn't contains ID");
        }
        authorRepository.insert(author);
    }

    @Override
    public void update(Author author) {
        if (Objects.isNull(author.getId())) {
            throw new UnsupportedOperationException("Entity should contains ID");
        }
        authorRepository.update(author);
    }

    @Override
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void deleteById(UUID id) {
        authorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findByNameAndLasName(String name, String lastName) {
        return authorRepository.findByNameAndLasName(name, lastName);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AuthorDto> findDtoByNameAndLasName(String name, String lastName) {
        return authorRepository.findDtoByNameAndLasName(name, lastName);
    }
}
