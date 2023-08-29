package ru.otus.spring.hw8.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw8.domain.Genre;
import ru.otus.spring.hw8.exception.ParentAssociationsException;
import ru.otus.spring.hw8.repository.GenreRepository;
import ru.otus.spring.hw8.service.BookService;
import ru.otus.spring.hw8.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final BookService bookService;

    @Override
    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        optionalGenre.ifPresent(bookService::existsByGenresContains);
        if (optionalGenre.isPresent()) {
            if (bookService.existsByGenresContains(optionalGenre.get())) {
                throw new ParentAssociationsException("Невозможно удалить жанр, есть связанные сущности");
            } else {
                genreRepository.deleteById(id);
            }
        }
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return genreRepository.findByName(name);
    }
}
