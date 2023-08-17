package ru.otus.spring.hw6.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.hw6.entity.Author;
import ru.otus.spring.hw6.entity.Book;
import ru.otus.spring.hw6.entity.Genre;
import ru.otus.spring.hw6.service.AuthorService;
import ru.otus.spring.hw6.service.BookService;
import ru.otus.spring.hw6.service.CommentService;
import ru.otus.spring.hw6.service.GenreService;
import ru.otus.spring.hw6.service.LibraryService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LibraryServiceImpl.class)
class LibraryServiceImplTest {

    @Autowired
    private LibraryService libraryService;

    @MockBean
    private BookService bookService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private CommentService commentService;

    @Test
    void getWithoutService() {
        UUID uuid = UUID.randomUUID();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        libraryService.get("Unknown", uuid.toString());

        Mockito.verify(authorService, Mockito.times(0)).findById(uuid);
        Mockito.verify(bookService, Mockito.times(0)).findById(uuid);
        Mockito.verify(commentService, Mockito.times(0)).findById(uuid);
        Mockito.verify(genreService, Mockito.times(0)).findById(uuid);

        Assertions.assertTrue(byteArrayOutputStream.toString().startsWith("Введите корректные параметры поиска"));
    }

    @Test
    void getAuthorService() {
        UUID uuid = UUID.randomUUID();

        libraryService.get("a", uuid.toString());

        Mockito.verify(authorService, Mockito.times(1)).findById(uuid);
        Mockito.verify(bookService, Mockito.times(0)).findById(uuid);
        Mockito.verify(commentService, Mockito.times(0)).findById(uuid);
        Mockito.verify(genreService, Mockito.times(0)).findById(uuid);

        libraryService.get("a", "all");

        Mockito.verify(authorService, Mockito.times(1)).findAll();
        Mockito.verify(bookService, Mockito.times(0)).findAll();
        Mockito.verify(commentService, Mockito.times(0)).findAll();
        Mockito.verify(genreService, Mockito.times(0)).findAll();
    }

    @Test
    void getBookService() {
        UUID uuid = UUID.randomUUID();

        libraryService.get("b", uuid.toString());

        Mockito.verify(authorService, Mockito.times(0)).findById(uuid);
        Mockito.verify(bookService, Mockito.times(1)).findById(uuid);
        Mockito.verify(commentService, Mockito.times(0)).findById(uuid);
        Mockito.verify(genreService, Mockito.times(0)).findById(uuid);

        libraryService.get("b", "all");

        Mockito.verify(authorService, Mockito.times(0)).findAll();
        Mockito.verify(bookService, Mockito.times(1)).findAll();
        Mockito.verify(commentService, Mockito.times(0)).findAll();
        Mockito.verify(genreService, Mockito.times(0)).findAll();
    }

    @Test
    void getGenreService() {
        UUID uuid = UUID.randomUUID();

        libraryService.get("g", uuid.toString());

        Mockito.verify(authorService, Mockito.times(0)).findById(uuid);
        Mockito.verify(bookService, Mockito.times(0)).findById(uuid);
        Mockito.verify(commentService, Mockito.times(0)).findById(uuid);
        Mockito.verify(genreService, Mockito.times(1)).findById(uuid);

        libraryService.get("g", "all");

        Mockito.verify(authorService, Mockito.times(0)).findAll();
        Mockito.verify(bookService, Mockito.times(0)).findAll();
        Mockito.verify(commentService, Mockito.times(0)).findAll();
        Mockito.verify(genreService, Mockito.times(1)).findAll();
    }

    @Test
    void getCommentService() {
        UUID uuid = UUID.randomUUID();

        libraryService.get("c", uuid.toString());

        Mockito.verify(authorService, Mockito.times(0)).findById(uuid);
        Mockito.verify(bookService, Mockito.times(0)).findById(uuid);
        Mockito.verify(commentService, Mockito.times(1)).findById(uuid);
        Mockito.verify(genreService, Mockito.times(0)).findById(uuid);

        libraryService.get("c", "all");

        Mockito.verify(authorService, Mockito.times(0)).findAll();
        Mockito.verify(bookService, Mockito.times(0)).findAll();
        Mockito.verify(commentService, Mockito.times(1)).findAll();
        Mockito.verify(genreService, Mockito.times(0)).findAll();
    }

    @Test
    void deleteWithoutService() {
        UUID uuid = UUID.randomUUID();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        libraryService.delete("Unknown", uuid.toString());

        Mockito.verify(authorService, Mockito.times(0)).deleteById(uuid);
        Mockito.verify(bookService, Mockito.times(0)).deleteById(uuid);
        Mockito.verify(commentService, Mockito.times(0)).deleteById(uuid);
        Mockito.verify(genreService, Mockito.times(0)).deleteById(uuid);

        Assertions.assertTrue(byteArrayOutputStream.toString().startsWith("Введите корректные параметры поиска"));

    }

    @Test
    void deleteAuthorService() {
        UUID uuid = UUID.randomUUID();

        libraryService.delete("a", uuid.toString());

        Mockito.verify(authorService, Mockito.times(1)).deleteById(uuid);
        Mockito.verify(bookService, Mockito.times(0)).deleteById(uuid);
        Mockito.verify(commentService, Mockito.times(0)).deleteById(uuid);
        Mockito.verify(genreService, Mockito.times(0)).deleteById(uuid);
    }

    @Test
    void deleteBookService() {
        UUID uuid = UUID.randomUUID();

        libraryService.delete("b", uuid.toString());

        Mockito.verify(authorService, Mockito.times(0)).deleteById(uuid);
        Mockito.verify(bookService, Mockito.times(1)).deleteById(uuid);
        Mockito.verify(commentService, Mockito.times(0)).deleteById(uuid);
        Mockito.verify(genreService, Mockito.times(0)).deleteById(uuid);
    }

    @Test
    void deleteGenreService() {
        UUID uuid = UUID.randomUUID();

        libraryService.delete("g", uuid.toString());

        Mockito.verify(authorService, Mockito.times(0)).deleteById(uuid);
        Mockito.verify(bookService, Mockito.times(0)).deleteById(uuid);
        Mockito.verify(commentService, Mockito.times(0)).deleteById(uuid);
        Mockito.verify(genreService, Mockito.times(1)).deleteById(uuid);
    }

    @Test
    void deleteCommentService() {
        UUID uuid = UUID.randomUUID();

        libraryService.delete("c", uuid.toString());

        Mockito.verify(authorService, Mockito.times(0)).deleteById(uuid);
        Mockito.verify(bookService, Mockito.times(0)).deleteById(uuid);
        Mockito.verify(commentService, Mockito.times(1)).deleteById(uuid);
        Mockito.verify(genreService, Mockito.times(0)).deleteById(uuid);
    }

    @Test
    void insertBook() {
        String bookTitle = "title";
        int pages = 123;
        int year = 2022;
        String authorName = "authorName";
        String authorLastName = "authorLastName";
        String genreName = "genreName";
        Author author = new Author(authorName, authorLastName);
        Genre genre = new Genre();
        genre.setName(genreName);
        when(authorService.findByNameAndLasName(authorName, authorLastName)).thenReturn(Optional.of(author));
        when(genreService.findByName(genreName)).thenReturn(Optional.of(genre));
        libraryService.insertBook(bookTitle, year, pages, authorName, authorLastName, genreName);

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        Mockito.verify(bookService, Mockito.times(1)).insert(captor.capture());
        Book updatedBook = captor.getValue();
        Assertions.assertEquals(bookTitle, updatedBook.getTitle());
        Assertions.assertEquals(pages, updatedBook.getPageCount());
        Assertions.assertEquals(year, updatedBook.getPublicationYear());
        Assertions.assertEquals(1, updatedBook.getGenres().size());
        Assertions.assertEquals(1, updatedBook.getAuthors().size());

    }

    @Test
    void updateBook() {
        UUID uuid = UUID.randomUUID();
        String title = "title";
        int pages = 123;
        int year = 2022;

        String newTitle = "NewTitle";
        int newPages = 1123;
        int newYear = 2222;

        Book book = new Book();
        book.setId(uuid);
        book.setTitle(title);
        book.setPageCount(pages);
        book.setPublicationYear(year);
        book.setAuthors(new HashSet<>());
        book.setGenres(new HashSet<>());
        book.setComments(new HashSet<>());

        when(bookService.findById(uuid)).thenReturn(Optional.of(book));
        libraryService.updateBook(uuid.toString(), newTitle, String.valueOf(newYear), String.valueOf(newPages));
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        Mockito.verify(bookService, Mockito.times(1)).update(captor.capture());
        Book updatedBook = captor.getValue();
        Assertions.assertEquals(uuid, updatedBook.getId());
        Assertions.assertEquals(newTitle, updatedBook.getTitle());
        Assertions.assertEquals(newPages, updatedBook.getPageCount());
        Assertions.assertEquals(newYear, updatedBook.getPublicationYear());
    }
}