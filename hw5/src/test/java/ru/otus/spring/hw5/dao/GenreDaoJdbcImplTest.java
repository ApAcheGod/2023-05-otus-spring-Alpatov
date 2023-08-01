package ru.otus.spring.hw5.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring.hw5.domain.Genre;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@JdbcTest
@ContextConfiguration(classes = GenreDaoJdbcImpl.class)
@DisplayName("Тест GenreDao")
class GenreDaoJdbcImplTest {

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    @DisplayName("Поиск по ID")
    public void findById() {
        UUID uuid = UUID.fromString("849f0952-43dc-4a30-81b9-8f846b272896");
        Genre genre = genreDaoJdbc.findById(uuid);
        Assertions.assertEquals("Утопия", genre.getName());
    }

    @Test
    @DisplayName("Поиск всех")
    public void findAll() {
        List<Genre> genres = genreDaoJdbc.findAll();
        Assertions.assertEquals(10, genres.size());
    }

    @Test
    @DisplayName("Поиск по названию")
    public void findByName() {
        Genre genre = genreDaoJdbc.findByName("Утопия");
        Assertions.assertEquals(UUID.fromString("849f0952-43dc-4a30-81b9-8f846b272896"), genre.getId());
    }

    @Test
    @DisplayName("Добавление записи")
    public void insert() {
        UUID genreUUID = UUID.randomUUID();
        String genreName = "Научная фантастика";
        Genre genre = new Genre(genreUUID, genreName);
        UUID uuid = genreDaoJdbc.insert(genre);
        Genre byId = genreDaoJdbc.findById(uuid);
        Assertions.assertEquals(genreUUID, byId.getId());
    }

    @Test
    @DisplayName("Обновление записи")
    public void update() {
        UUID uuid = UUID.fromString("849f0952-43dc-4a30-81b9-8f846b272896");
        String name = "Учебное пособие";
        Genre genre = genreDaoJdbc.findById(uuid);
        genre.setName(name);
        genreDaoJdbc.update(genre);
        Genre genre2 = genreDaoJdbc.findById(uuid);
        Assertions.assertEquals(name, genre2.getName());
    }

    @Test
    @DisplayName("Удаление по ID")
    public void delete() {
        UUID uuid = UUID.fromString("849f0952-43dc-4a30-81b9-8f846b272896");
        genreDaoJdbc.deleteById(uuid);

        assertThatThrownBy(() -> genreDaoJdbc.findById(uuid))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}