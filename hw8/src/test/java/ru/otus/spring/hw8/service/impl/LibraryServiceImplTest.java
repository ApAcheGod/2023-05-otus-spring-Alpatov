package ru.otus.spring.hw8.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.hw8.domain.Author;
import ru.otus.spring.hw8.domain.Book;
import ru.otus.spring.hw8.domain.Comment;
import ru.otus.spring.hw8.domain.Genre;
import ru.otus.spring.hw8.event.BookDeleteListener;
import ru.otus.spring.hw8.event.CascadeSaveMongoEventListener;
import ru.otus.spring.hw8.event.SequenceMongoEventListener;
import ru.otus.spring.hw8.service.AuthorService;
import ru.otus.spring.hw8.service.BookService;
import ru.otus.spring.hw8.service.CommentService;
import ru.otus.spring.hw8.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataMongoTest
@Import(value = {
        BookServiceImpl.class,
        CommentServiceImpl.class,
        GenreServiceImpl.class,
        AuthorServiceImpl.class,
        BookDeleteListener.class,
        CascadeSaveMongoEventListener.class,
        SequenceMongoEventListener.class,
        LibrarySequenceGenerator.class
})
class LibraryServiceImplTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthorService authorService;

    @Test
    void get() {

        Optional<Book> optionalBook = bookService.findById(1L);
        Assertions.assertTrue(optionalBook.isPresent());
        Assertions.assertEquals("Мастер и Маргарита", optionalBook.get().getTitle());
    }

    @Test
    void delete() {
        List<Comment> comments = commentService.findAll();
        bookService.deleteById(2L);

        Optional<Book> optionalBook = bookService.findById(2L);
        Assertions.assertFalse(optionalBook.isPresent());

        List<Comment> commentsAfterBookDelete = commentService.findAll();

        Assertions.assertTrue(commentsAfterBookDelete.size() < comments.size());


    }

    @Test
    void save(){
        Author author = new Author();
        author.setName("name");
        author.setLastName("lastName");

        Genre genre = new Genre();
        genre.setName("genre");
        Book book = new Book("new title", 123, 123,
                List.of(author), List.of(genre), new ArrayList<>());
        bookService.save(book);
        Optional<Book> optionalSavedBook = bookService.findByTitle("new title");

        Assertions.assertTrue(optionalSavedBook.isPresent());
        Assertions.assertNotNull(optionalSavedBook.get().getId());
        Assertions.assertNotNull(optionalSavedBook.get().getAuthors());
        Assertions.assertEquals(1, optionalSavedBook.get().getAuthors().size());
        Assertions.assertNotNull(optionalSavedBook.get().getGenres());
        Assertions.assertEquals(1, optionalSavedBook.get().getGenres().size());
    }

    @Test
    void existsByGenresContains(){
        Optional<Genre> genre = genreService.findById(1L);
        Assertions.assertTrue(bookService.existsByGenresContains(genre.get()));

        Genre unExistingGenre = new Genre();
        unExistingGenre.setId(101L);
        unExistingGenre.setName("gg");
        Assertions.assertFalse(bookService.existsByGenresContains(unExistingGenre));
    }

    @Test
    void existsByAuthorsContains(){
        Optional<Author> author = authorService.findById(1L);
        Assertions.assertTrue(bookService.existsByAuthorsContains(author.get()));

        Author unExistingAuthor = new Author();
        unExistingAuthor.setId(101L);
        unExistingAuthor.setName("name");
        unExistingAuthor.setLastName("lastName");
        Assertions.assertFalse(bookService.existsByAuthorsContains(unExistingAuthor));
    }
}