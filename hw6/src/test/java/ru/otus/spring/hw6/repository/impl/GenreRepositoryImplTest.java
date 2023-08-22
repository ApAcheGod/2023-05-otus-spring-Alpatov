package ru.otus.spring.hw6.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.hw6.entity.Genre;
import ru.otus.spring.hw6.repository.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@DataJpaTest
@Import(GenreRepositoryImpl.class)
@DisplayName("Тест GenreRepositoryImpl")
class GenreRepositoryImplTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Поиск по ID")
    public void findById() {
        UUID uuid = UUID.fromString("849f0952-43dc-4a30-81b9-8f846b272896");
        Optional<Genre> optionalGenre = genreRepository.findById(uuid);
        Assertions.assertTrue(optionalGenre.isPresent());
        Assertions.assertEquals("Утопия", optionalGenre.get().getName());
    }

    @Test
    @DisplayName("Поиск всех")
    public void findAll() {
        List<Genre> genres = genreRepository.findAll();
        Assertions.assertEquals(10, genres.size());
    }

    @Test
    @DisplayName("Поиск по названию")
    public void findByName() {
        Optional<Genre> optionalGenre = genreRepository.findByName("Утопия");
        Assertions.assertTrue(optionalGenre.isPresent());
        Assertions.assertEquals(UUID.fromString("849f0952-43dc-4a30-81b9-8f846b272896"), optionalGenre.get().getId());
    }

    @Test
    @DisplayName("Добавление записи")
    public void insert() {
        String genreName = "Научная фантастика";
        Genre genre = new Genre(null, genreName);
        genreRepository.insert(genre);
        Optional<Genre> optionalGenre = genreRepository.findByName(genreName);
        Assertions.assertTrue(optionalGenre.isPresent());
    }

    @Test
    @DisplayName("Обновление записи")
    public void update() {
        UUID uuid = UUID.fromString("849f0952-43dc-4a30-81b9-8f846b272896");
        String name = "Учебное пособие";
        Optional<Genre> optionalGenre = genreRepository.findById(uuid);
        Assertions.assertTrue(optionalGenre.isPresent());
        optionalGenre.get().setName(name);
        genreRepository.update(optionalGenre.get());
        Optional<Genre> optionalGenre1 = genreRepository.findById(uuid);
        Assertions.assertTrue(optionalGenre1.isPresent());
        Assertions.assertEquals(name, optionalGenre1.get().getName());
    }

    @Test
    @DisplayName("Удаление по ID")
    public void delete() {
        UUID uuid = UUID.fromString("849f0952-43dc-4a30-81b9-8f846b272896");
        genreRepository.deleteById(uuid);

        Optional<Genre> optionalGenre = genreRepository.findById(uuid);
        Assertions.assertEquals(Optional.empty(), optionalGenre);
    }
}