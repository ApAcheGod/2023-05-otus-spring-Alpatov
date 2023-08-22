package ru.otus.spring.hw7.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw7.entity.Book;
import ru.otus.spring.hw7.repository.BookRepository;
import ru.otus.spring.hw7.service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Optional<Book> findById(UUID id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
}
