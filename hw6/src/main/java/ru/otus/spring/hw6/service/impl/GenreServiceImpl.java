package ru.otus.spring.hw6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw6.entity.Genre;
import ru.otus.spring.hw6.repository.GenreRepository;
import ru.otus.spring.hw6.service.GenreService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> findById(UUID id) {
        return genreRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public void insert(Genre genre) {
        if (Objects.nonNull(genre.getId())) {
            throw new UnsupportedOperationException("Entity shouldn't contains ID");
        }
        genreRepository.insert(genre);
    }

    @Override
    public void update(Genre genre) {
        if (Objects.isNull(genre.getId())) {
            throw new UnsupportedOperationException("Entity should contains ID");
        }
        genreRepository.update(genre);
    }

    @Override
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void deleteById(UUID id) {
        genreRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> findByName(String name) {
        return genreRepository.findByName(name);
    }
}
