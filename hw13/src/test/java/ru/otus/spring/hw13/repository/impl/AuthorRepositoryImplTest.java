package ru.otus.spring.hw13.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.hw13.entity.Author;
import ru.otus.spring.hw13.entity.Book;
import ru.otus.spring.hw13.repository.AuthorRepository;

import java.util.*;

@DataJpaTest
//@Import(AuthorRepositoryImpl.class)
@DisplayName("Тест AuthorRepositoryImpl")
class AuthorRepositoryImplTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Поиск по ID")
    void findById() {
        UUID uuid = UUID.fromString("2ed2a976-7826-47d4-8015-f43d86b863ac");
        String name = "Михаил";
        String lastName = "Булгаков";
        Optional<Author> optionalAuthor = authorRepository.findById(uuid);
        Assertions.assertTrue(optionalAuthor.isPresent());
        Assertions.assertEquals(name, optionalAuthor.get().getName());
        Assertions.assertEquals(lastName, optionalAuthor.get().getLastName());
    }

    @Test
    @DisplayName("Поиск всех")
    void findAll() {
        List<Author> authors = authorRepository.findAll();
        Assertions.assertEquals(7, authors.size());
    }

    @Test
    void insert() {
        String name = "name";
        String lastName = "lastName";

        Book book = new Book(null, "title", 123, 321, new HashSet<>(),new HashSet<>(), new HashSet<>());

        Author author = new Author(null, name, lastName, Set.of(book));
        authorRepository.save(author);

        Optional<Author> optionalAuthor = authorRepository.findByNameAndLastName(name, lastName);
        Assertions.assertTrue(optionalAuthor.isPresent());
        Assertions.assertEquals(name, optionalAuthor.get().getName());
        Assertions.assertEquals(lastName, optionalAuthor.get().getLastName());
        Assertions.assertEquals(1, optionalAuthor.get().getBooks().size());
    }

    @Test
    void update() {
        UUID uuid = UUID.fromString("2ed2a976-7826-47d4-8015-f43d86b863ac");
        String name = "name";
        String lastName = "lastName";

        Optional<Author> optionalAuthor = authorRepository.findById(uuid);
        Assertions.assertTrue(optionalAuthor.isPresent());
        optionalAuthor.get().setName(name);
        optionalAuthor.get().setLastName(lastName);
        authorRepository.save(optionalAuthor.get());

        optionalAuthor = authorRepository.findById(uuid);

        Assertions.assertTrue(optionalAuthor.isPresent());
        Assertions.assertEquals(name, optionalAuthor.get().getName());
        Assertions.assertEquals(lastName, optionalAuthor.get().getLastName());
    }

    @Test
    @DisplayName("Удаление записи")
    void deleteById() {
        UUID uuid = UUID.fromString("2ed2a976-7826-47d4-8015-f43d86b863ac");
        authorRepository.deleteById(uuid);

        Assertions.assertEquals(Optional.empty(), authorRepository.findById(uuid));}

    @Test
    void findByNameAndLasName() {
        String name = "Михаил";
        String lastName = "Булгаков";
        Optional<Author> optionalAuthor = authorRepository.findByNameAndLastName(name, lastName);

        Assertions.assertTrue(optionalAuthor.isPresent());
        Assertions.assertEquals(name, optionalAuthor.get().getName());
        Assertions.assertEquals(lastName, optionalAuthor.get().getLastName());

    }
}