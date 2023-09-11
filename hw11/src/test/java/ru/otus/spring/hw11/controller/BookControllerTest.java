package ru.otus.spring.hw11.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw11.controller.BookController;
import ru.otus.spring.hw11.domain.Book;
import ru.otus.spring.hw11.domain.Comment;
import ru.otus.spring.hw11.dto.BookDto;
import ru.otus.spring.hw11.dto.CommentDto;
import ru.otus.spring.hw10.mapper.*;
import ru.otus.spring.hw11.mapper.BookMapper;
import ru.otus.spring.hw11.mapper.CommentMapper;
import ru.otus.spring.hw11.repository.BookRepository;
import ru.otus.spring.hw11.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



@WebFluxTest(BookController.class)
@Import(value = {BookMapperImpl.class, AuthorMapperImpl.class, CommentMapperImpl.class})
class BookControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CommentMapper commentMapper;

    @MockBean
    private CommentRepository commentRepository;


    @Test
    void getBookById() {
        String bookRequestId = "1";
        Book expectedBook = new Book();
        expectedBook.setId(bookRequestId);
        BookDto expectedResponse = bookMapper.toDto(expectedBook);

        given(bookRepository.findById(bookRequestId)).willReturn(Mono.just(expectedBook));

        BookDto actualResponse = webTestClient
                .get()
                .uri("/api/book/" + bookRequestId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void getBooks() {
        String bookRequestId = "1";
        Book expectedBook = new Book();
        expectedBook.setId(bookRequestId);
        List<Book> expectedList = List.of(expectedBook);
        List<BookDto> expectedResponse = expectedList.stream().map(bookMapper::toDto).collect(Collectors.toList());

        given(bookRepository.findAll()).willReturn(Flux.fromIterable(expectedList));

        List<BookDto> actualResponse = webTestClient
                .get()
                .uri("/api/book")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class).hasSize(expectedResponse.size())
                .returnResult()
                .getResponseBody();

        assertThat(actualResponse).containsExactlyInAnyOrderElementsOf(expectedResponse);
    }

    @Test
    void getComment() {
        String bookRequestId = "1";
        Book expectedBook = new Book();
        expectedBook.setId(bookRequestId);
        Comment comment = new Comment("1", "comment", expectedBook);
        List<CommentDto> expectedResponse = List.of(commentMapper.toDto(comment));

        given(bookRepository.findById(bookRequestId)).willReturn(Mono.just(expectedBook));
        given(commentRepository.findAllByBookId(bookRequestId)).willReturn(Flux.just(comment));

        List<CommentDto> actualResponse = webTestClient
                .get()
                .uri("/api/book/" + bookRequestId + "/comment")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDto.class).hasSize(expectedResponse.size())
                .returnResult()
                .getResponseBody();

        assertThat(actualResponse).containsExactlyInAnyOrderElementsOf(expectedResponse);
    }

    @Test
    void createBook() {
        Book expectedBook = new Book();
        BookDto expectedResponse = bookMapper.toDto(expectedBook);

        given(bookRepository.save(expectedBook)).willReturn(Mono.just(expectedBook));

        BookDto actualResponse = webTestClient
                .post()
                .uri("/api/book")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(expectedResponse), BookDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void updateBook() {
        String bookRequestId = "1";
        Book expectedBook = new Book();
        expectedBook.setId(bookRequestId);
        BookDto expectedResponse = bookMapper.toDto(expectedBook);

        given(bookRepository.save(expectedBook)).willReturn(Mono.just(expectedBook));

        BookDto actualResponse = webTestClient
                .post()
                .uri("/api/book")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(expectedResponse), BookDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void deleteById() {
        String bookRequestId = "1";

        given(bookRepository.deleteById(bookRequestId)).willReturn(Mono.empty());
        given(commentRepository.deleteAllByBookId(bookRequestId)).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/book/" + bookRequestId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        verify(bookRepository, times(1)).deleteById(bookRequestId);
        verify(commentRepository, times(1)).deleteAllByBookId(bookRequestId);
    }
}