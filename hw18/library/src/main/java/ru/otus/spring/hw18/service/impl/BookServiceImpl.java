package ru.otus.spring.hw18.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw18.entity.Book;
import ru.otus.spring.hw18.repository.BookRepository;
import ru.otus.spring.hw18.service.BookService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @CircuitBreaker(name = "bookServiceCircuitBreaker", fallbackMethod = "getBookFallback")
    public Optional<Book> findById(UUID id) {
        return bookRepository.findById(id);
    }

    @Override
    @CircuitBreaker(name = "bookServiceCircuitBreaker", fallbackMethod = "getBookFallback")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(UUID id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> getBookFallback(Throwable e) {
//        log.info("fallbackBook method called: {}", e.getMessage());
        return Collections.singletonList(new Book(null, "N/A", null, null, null, null, null));
    }
}
