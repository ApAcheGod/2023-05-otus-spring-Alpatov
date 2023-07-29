package ru.otus.spring.hw5.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring.hw5.domain.Author;
import ru.otus.spring.hw5.domain.Book;
import ru.otus.spring.hw5.domain.Genre;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@JdbcTest
@ContextConfiguration(classes = BookDaoJdbcImpl.class)
@DisplayName("Тест BookDao")
class BookDaoJdbcImplTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    @DisplayName("Поиск по ID")
    public void findById() {
        UUID uuid = UUID.fromString("80e9ace3-1a83-40ae-b812-32b92785fb3b");
        String bookTitle = "Мастер и Маргарита";
        Book book = bookDaoJdbc.findById(uuid);
        Assertions.assertEquals(bookTitle, book.getTitle());

    }

    @Test
    @DisplayName("Поиск всех")
    public void findAll() {
        List<Book> books = bookDaoJdbc.findAll();
        Assertions.assertEquals(7, books.size());
    }

    @Test
    @DisplayName("Добавление записи")
    public void insert() {
        UUID bookUuid = UUID.randomUUID();
        String bookTitle = "Title";
        int pageCount = 999;
        int year = 2001;

        UUID genreUuid = UUID.fromString("849f0952-43dc-4a30-81b9-8f846b272896");
        UUID authorUuid = UUID.fromString("4e37b705-4dd9-48f3-93b1-395668123335");

        Author author = new Author();
        author.setId(authorUuid);

        Genre genre = new Genre();
        genre.setId(genreUuid);

        Book book = new Book(bookUuid, bookTitle, year, pageCount, Set.of(author), Set.of(genre));

        UUID resultUuid = bookDaoJdbc.insert(book);
        Book resultBook = bookDaoJdbc.findById(resultUuid);

        Assertions.assertEquals(bookUuid, resultUuid);
        Assertions.assertEquals(bookTitle, resultBook.getTitle());
        Assertions.assertEquals(pageCount, resultBook.getPageCount());
        Assertions.assertEquals(year, resultBook.getPublicationYear());
        Assertions.assertEquals(1, resultBook.getAuthors().size());
        Assertions.assertEquals(1, resultBook.getGenres().size());
    }

    @Test
    @DisplayName("Обновление записи")
    public void update() {
        UUID uuid = UUID.fromString("80e9ace3-1a83-40ae-b812-32b92785fb3b");
        String bookTitle = "Title";
        int pageCount = 999;
        int year = 2001;

        Book book = bookDaoJdbc.findById(uuid);
        book.setTitle(bookTitle);
        book.setPageCount(pageCount);
        book.setPublicationYear(year);

        bookDaoJdbc.update(book);
        book = bookDaoJdbc.findById(uuid);
        Assertions.assertEquals(bookTitle, book.getTitle());
        Assertions.assertEquals(year, book.getPublicationYear());
        Assertions.assertEquals(pageCount, book.getPageCount());

    }

    @Test
    @DisplayName("Удаление записи")
    public void delete() {
        UUID uuid = UUID.fromString("80e9ace3-1a83-40ae-b812-32b92785fb3b");
        bookDaoJdbc.deleteById(uuid);

        Assertions.assertNull(bookDaoJdbc.findById(uuid));
    }

    @Test
    @DisplayName("Поиск по названию")
    public void findByTitle() {
        UUID uuid = UUID.fromString("80e9ace3-1a83-40ae-b812-32b92785fb3b");
        String bookTitle = "Мастер и Маргарита";
        Book book = bookDaoJdbc.findByTitle(bookTitle);
        Assertions.assertEquals(uuid, book.getId());
    }

}