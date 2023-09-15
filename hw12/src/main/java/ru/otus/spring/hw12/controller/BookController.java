package ru.otus.spring.hw12.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.hw12.annotation.EntityNotFoundExceptionHandler;
import ru.otus.spring.hw12.dto.BookDto;
import ru.otus.spring.hw12.dto.CommentDto;
import ru.otus.spring.hw12.entity.Book;
import ru.otus.spring.hw12.mapper.BookMapper;
import ru.otus.spring.hw12.mapper.CommentMapper;
import ru.otus.spring.hw12.service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@EntityNotFoundExceptionHandler
public class BookController {

    private final BookService bookService;

    private final BookMapper bookMapper;

    private final CommentMapper commentMapper;

    @GetMapping("/api/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") UUID id) {
        Optional<Book> optionalBook = bookService.findById(id);
        if (optionalBook.isPresent()) {
            return ResponseEntity.ok(bookMapper.toDto(optionalBook.get()));
        } else {
            throw new EntityNotFoundException();
        }
    }

    @GetMapping("/api/book")
    public List<BookDto> getBooks() {
        return bookService.findAll()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/book/{id}/comment")
    public List<CommentDto> getComment(@PathVariable("id") UUID id) {
        Optional<Book> optionalBook = bookService.findById(id);
        if (optionalBook.isPresent()) {
            return optionalBook.get().getComments().stream().map(commentMapper::toDto).collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @PostMapping("/api/book")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        bookService.save(book);
        return ResponseEntity.ok(bookMapper.toDto(book));
    }

    @PutMapping("/api/book")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        bookService.save(book);
        return ResponseEntity.ok(bookMapper.toDto(book));
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        bookService.deleteById(id);
    }
}
