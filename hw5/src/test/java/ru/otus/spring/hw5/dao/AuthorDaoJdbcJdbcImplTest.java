package ru.otus.spring.hw5.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring.hw5.domain.Author;
import ru.otus.spring.hw5.domain.Book;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@JdbcTest
@ContextConfiguration(classes = AuthorDaoJdbcJdbcImpl.class)
@DisplayName("Тест AuthorDao")
class AuthorDaoJdbcJdbcImplTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    @DisplayName("Поиск по ID")
    void findById() {
        UUID uuid = UUID.fromString("2ed2a976-7826-47d4-8015-f43d86b863ac");
        String name = "Михаил";
        String lastName = "Булгаков";
        Author author = authorDaoJdbc.findById(uuid);
        Assertions.assertEquals(name, author.getName());
        Assertions.assertEquals(lastName, author.getLastName());
    }

    @Test
    @DisplayName("Поиск всех")
    void findAll() {
        List<Author> authors = authorDaoJdbc.findAll();
        Assertions.assertEquals(7, authors.size());
    }

    @Test
    void insert() {
        UUID id = UUID.randomUUID();
        String name = "name";
        String lastName = "lastName";

        Book book = new Book();
        book.setId(UUID.fromString("80e9ace3-1a83-40ae-b812-32b92785fb3b"));

        Author author = new Author(id, name, lastName, Set.of(book));
        authorDaoJdbc.insert(author);

        author = authorDaoJdbc.findById(id);
        Assertions.assertEquals(id, author.getId());
        Assertions.assertEquals(name, author.getName());
        Assertions.assertEquals(lastName, author.getLastName());
        Assertions.assertEquals(1, author.getBooks().size());
    }

    @Test
    void update() {
        UUID uuid = UUID.fromString("2ed2a976-7826-47d4-8015-f43d86b863ac");
        String name = "name";
        String lastName = "lastName";

        Author author = authorDaoJdbc.findById(uuid);
        author.setName(name);
        author.setLastName(lastName);
        authorDaoJdbc.update(author);

        author = authorDaoJdbc.findById(uuid);

        Assertions.assertEquals(name, author.getName());
        Assertions.assertEquals(lastName, author.getLastName());
    }

    @Test
    @DisplayName("Удаление записи")
    void deleteById() {
        UUID uuid = UUID.fromString("2ed2a976-7826-47d4-8015-f43d86b863ac");
        authorDaoJdbc.deleteById(uuid);

        Assertions.assertNull(authorDaoJdbc.findById(uuid));}

    @Test
    void findByNameAndLasName() {
        UUID uuid = UUID.fromString("2ed2a976-7826-47d4-8015-f43d86b863ac");
        String name = "Михаил";
        String lastName = "Булгаков";
        Author author = authorDaoJdbc.findByNameAndLasName(name, lastName);
        Assertions.assertEquals(name, author.getName());
        Assertions.assertEquals(lastName, author.getLastName());

    }
}