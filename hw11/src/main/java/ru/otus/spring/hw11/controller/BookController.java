package ru.otus.spring.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw11.dto.BookDto;
import ru.otus.spring.hw11.dto.CommentDto;
import ru.otus.spring.hw11.mapper.BookMapper;
import ru.otus.spring.hw11.mapper.CommentMapper;
import ru.otus.spring.hw11.repository.BookRepository;
import ru.otus.spring.hw11.repository.CommentRepository;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final CommentMapper commentMapper;

    private final CommentRepository commentRepository;

    @GetMapping("/api/book/{id}")
    public Mono<ResponseEntity<BookDto>> getBookById(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @GetMapping(value = "/api/book")
    public Flux<BookDto> getBooks() {
        return bookRepository.findAll()
                .map(bookMapper::toDto);
    }


    @GetMapping("/api/book/{id}/comment")
    public Flux<CommentDto> getComment(@PathVariable("id") String id) {
        return commentRepository.findAllByBookId(id)
                .map(commentMapper::toDto);
    }


    @PostMapping("/api/book")
    public Mono<ResponseEntity<BookDto>> createBook(@RequestBody BookDto bookDto) {
        return bookRepository.save(bookMapper.toEntity(bookDto))
                .map(bookMapper::toDto)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/api/book")
    public Mono<ResponseEntity<BookDto>> updateBook(@RequestBody BookDto bookDto) {
        return bookRepository.save(bookMapper.toEntity(bookDto))
                .map(bookMapper::toDto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/api/book/{id}")
    public Mono<Void> deleteById(@PathVariable("id") String id) {
        return bookRepository.deleteById(id)
                .then(commentRepository.deleteAllByBookId(id));
    }
}
