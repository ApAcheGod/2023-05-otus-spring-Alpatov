package ru.otus.spring.hw9.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.hw9.dto.BookDto;
import ru.otus.spring.hw9.dto.CommentDto;
import ru.otus.spring.hw9.entity.Author;
import ru.otus.spring.hw9.entity.Book;
import ru.otus.spring.hw9.entity.Comment;
import ru.otus.spring.hw9.entity.Genre;
import ru.otus.spring.hw9.mapper.AuthorMapper;
import ru.otus.spring.hw9.mapper.AuthorMapperImpl;
import ru.otus.spring.hw9.mapper.BookMapper;
import ru.otus.spring.hw9.mapper.BookMapperImpl;
import ru.otus.spring.hw9.mapper.CommentMapper;
import ru.otus.spring.hw9.mapper.CommentMapperImpl;
import ru.otus.spring.hw9.service.BookService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import({BookMapperImpl.class, AuthorMapperImpl.class, CommentMapperImpl.class})
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CommentMapper commentMapper;

    @Test
    void getBookById() throws Exception {
        UUID bookId = UUID.randomUUID();
        String bookTitle = "Title";
        int pageCount = 999;
        int year = 2001;

        Author author = new Author(UUID.randomUUID(), "name", "lastname", new HashSet<>());
        Genre genre = new Genre(UUID.randomUUID(), "name");

        Book book = new Book(bookId, bookTitle, year, pageCount, Set.of(author), Set.of(genre), new HashSet<>());
        when(bookService.findById(bookId)).thenReturn(Optional.of(book));
        BookDto expectedResult = bookMapper.toDto(book);

        mvc.perform(get("/api/book/" + bookId))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void getBooks() throws Exception {
        UUID bookId = UUID.randomUUID();
        String bookTitle = "Title";
        int pageCount = 999;
        int year = 2001;

        Author author = new Author(UUID.randomUUID(), "name", "lastname", new HashSet<>());
        Genre genre = new Genre(UUID.randomUUID(), "name");

        Book book = new Book(bookId, bookTitle, year, pageCount, Set.of(author), Set.of(genre), new HashSet<>());

        UUID bookId2 = UUID.randomUUID();
        String bookTitle2 = "Title";
        int pageCount2 = 999;
        int year2 = 2001;

        Author author2 = new Author(UUID.randomUUID(), "name", "lastname", new HashSet<>());
        Genre genre2 = new Genre(UUID.randomUUID(), "name");

        Book book2 = new Book(bookId2, bookTitle2, year2, pageCount2, Set.of(author2), Set.of(genre2), new HashSet<>());

        List<Book> books = List.of(book, book2);
        when(bookService.findAll()).thenReturn(books);

        List<BookDto> expectedResult = books.stream().map(bookMapper::toDto).toList();

        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));

    }

    @Test
    void getComment() throws Exception {
        UUID bookId = UUID.randomUUID();
        String bookTitle = "Title";
        int pageCount = 999;
        int year = 2001;

        Author author = new Author(UUID.randomUUID(), "name", "lastname", new HashSet<>());
        Genre genre = new Genre(UUID.randomUUID(), "name");

        Book book = new Book(bookId, bookTitle, year, pageCount, Set.of(author), Set.of(genre), new HashSet<>());

        Comment comment = new Comment(UUID.randomUUID(), "comment", book);
        book.getComments().add(comment);

        when(bookService.findById(bookId)).thenReturn(Optional.of(book));
        List<CommentDto> expectedResult = book.getComments().stream().map(commentMapper::toDto).toList();

        mvc.perform(get("/api/book/" + bookId + "/comment"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void createBook() throws Exception {
        UUID bookId = UUID.randomUUID();
        String bookTitle = "Title";
        int pageCount = 999;
        int year = 2001;

        Author author = new Author(UUID.randomUUID(), "name", "lastname", new HashSet<>());
        Genre genre = new Genre(UUID.randomUUID(), "name");

        Book book = new Book(bookId, bookTitle, year, pageCount, Set.of(author), Set.of(genre), new HashSet<>());

        when(bookService.save(any())).thenReturn(book);

        String expectedResult = mapper.writeValueAsString(bookMapper.toDto(book));

        mvc.perform(post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));

    }

    @Test
    void updateBook() throws Exception {
        UUID bookId = UUID.randomUUID();
        String bookTitle = "Title";
        int pageCount = 999;
        int year = 2001;

        Author author = new Author(UUID.randomUUID(), "name", "lastname", new HashSet<>());
        Genre genre = new Genre(UUID.randomUUID(), "name");

        Book book = new Book(bookId, bookTitle, year, pageCount, Set.of(author), Set.of(genre), new HashSet<>());
        when(bookService.findById(bookId)).thenReturn(Optional.of(book));

        book.setTitle("newTitle");
        String expectedResult = mapper.writeValueAsString(bookMapper.toDto(book));

        mvc.perform(put("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));


    }

    @Test
    void deleteById() throws Exception {
        UUID uuid = UUID.randomUUID();
        mvc.perform(delete("/api/book/" + uuid))
                .andExpect(status().isOk());
        verify(bookService, times(1)).deleteById(uuid);
    }
}