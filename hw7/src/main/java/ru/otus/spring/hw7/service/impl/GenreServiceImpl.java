package ru.otus.spring.hw7.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw7.entity.Genre;
import ru.otus.spring.hw7.repository.GenreRepository;
import ru.otus.spring.hw7.service.GenreService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Optional<Genre> findById(UUID id) {
        return genreRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        genreRepository.deleteById(id);
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return genreRepository.findByName(name);
    }
}
