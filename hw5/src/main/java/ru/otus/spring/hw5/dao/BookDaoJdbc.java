package ru.otus.spring.hw5.dao;

import ru.otus.spring.hw5.domain.Book;

import java.util.List;
import java.util.UUID;

public interface BookDaoJdbc {

    Book findById(UUID id);

    List<Book> findAll();

    UUID insert(Book book);

    void update(Book book);

    void deleteById(UUID id);

    Book findByTitle(String title);

}
