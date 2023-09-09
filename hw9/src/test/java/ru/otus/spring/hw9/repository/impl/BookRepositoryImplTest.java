package ru.otus.spring.hw9.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.hw9.entity.Author;
import ru.otus.spring.hw9.entity.Book;
import ru.otus.spring.hw9.entity.Genre;
import ru.otus.spring.hw9.repository.BookRepository;

import java.util.*;

@DataJpaTest
//@Import(BookRepositoryImpl.class)
@DisplayName("Тест BookRepositoryImpl")
@ExtendWith(SpringExtension.class)
class BookRepositoryImplTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Поиск по ID")
    public void findById() {
        UUID uuid = UUID.fromString("80e9ace3-1a83-40ae-b812-32b92785fb3b");
        String bookTitle = "Мастер и Маргарита";
        Optional<Book> optionalBook = bookRepository.findById(uuid);
        Assertions.assertTrue(optionalBook.isPresent());
        Assertions.assertEquals(bookTitle, optionalBook.get().getTitle());
    }

    @Test
    @DisplayName("Поиск всех")
    public void findAll() {
        List<Book> books = bookRepository.findAll();
        Assertions.assertEquals(7, books.size());
    }

    @Test
    @DisplayName("Добавление записи")
    public void insert() {
        String bookTitle = "Title";
        int pageCount = 999;
        int year = 2001;

        Author author = new Author("name", "lastname");
        Genre genre = new Genre(null, "name");

        Book book = new Book(null, bookTitle, year, pageCount, Set.of(author), Set.of(genre), new HashSet<>());

        bookRepository.save(book);
        Optional<Book> optionalBook = bookRepository.findByTitle(bookTitle);

        Assertions.assertTrue(optionalBook.isPresent());
        Assertions.assertEquals(bookTitle, optionalBook.get().getTitle());
        Assertions.assertEquals(pageCount, optionalBook.get().getPageCount());
        Assertions.assertEquals(year, optionalBook.get().getPublicationYear());
        Assertions.assertEquals(1, optionalBook.get().getAuthors().size());
        Assertions.assertEquals(1, optionalBook.get().getGenres().size());
    }

    @Test
    @DisplayName("Обновление записи")
    public void update() {
        UUID uuid = UUID.fromString("80e9ace3-1a83-40ae-b812-32b92785fb3b");
        String bookTitle = "Мастер и Маргарита";
        int pageCount = 999;
        int year = 2001;

        Optional<Book> optionalBook = bookRepository.findByTitle(bookTitle);
        Assertions.assertTrue(optionalBook.isPresent());

        optionalBook.get().setTitle(bookTitle);
        optionalBook.get().setPageCount(pageCount);
        optionalBook.get().setPublicationYear(year);

        bookRepository.save(optionalBook.get());
        Optional<Book> optionalBook1 = bookRepository.findById(uuid);
        Assertions.assertEquals(bookTitle, optionalBook1.get().getTitle());
        Assertions.assertEquals(year, optionalBook1.get().getPublicationYear());
        Assertions.assertEquals(pageCount, optionalBook1.get().getPageCount());

    }

    @Test
    @DisplayName("Удаление записи")
    public void delete() {
        UUID uuid = UUID.fromString("80e9ace3-1a83-40ae-b812-32b92785fb3b");
        bookRepository.deleteById(uuid);

        Assertions.assertEquals(Optional.empty(),bookRepository.findById(uuid));
    }

    @Test
    @DisplayName("Поиск по названию")
    public void findByTitle() {
        UUID uuid = UUID.fromString("80e9ace3-1a83-40ae-b812-32b92785fb3b");
        String bookTitle = "Мастер и Маргарита";
        Optional<Book> optionalBook = bookRepository.findByTitle(bookTitle);
        Assertions.assertTrue(optionalBook.isPresent());
        Assertions.assertEquals(uuid, optionalBook.get().getId());
    }

}