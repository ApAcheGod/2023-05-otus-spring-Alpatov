package ru.otus.spring.hw5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw5.dao.AuthorDaoJdbc;
import ru.otus.spring.hw5.dao.BookDaoJdbc;
import ru.otus.spring.hw5.dao.GenreDaoJdbc;
import ru.otus.spring.hw5.domain.Author;
import ru.otus.spring.hw5.domain.Book;
import ru.otus.spring.hw5.domain.Genre;
import ru.otus.spring.hw5.utils.PrinterUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LibraryImpl implements Library {

    private final BookDaoJdbc bookDaoJdbc;

    private final GenreDaoJdbc genreDaoJdbc;

    private final AuthorDaoJdbc authorDaoJdbc;

    @Override
    public void getBook(String id) {
        if ("all".equals(id)) {
            bookDaoJdbc.findAll().forEach(PrinterUtils::printBook);
        } else {
            PrinterUtils.printBook(bookDaoJdbc.findById(UUID.fromString(id)));
        }
    }

    @Override
    public void getGenre(String id) {
        if ("all".equals(id)) {
            System.out.println(genreDaoJdbc.findAll());
        } else {
            System.out.println(genreDaoJdbc.findById(UUID.fromString(id)));
        }
    }

    @Override
    public void getAuthor(String id) {
        if ("all".equals(id)) {
            System.out.println(authorDaoJdbc.findAll());
        } else {
            System.out.println(authorDaoJdbc.findById(UUID.fromString(id)));
        }
    }

    @Override
    public void deleteBook(String id) {
        bookDaoJdbc.deleteById(UUID.fromString(id));
        System.out.println("Книга удалена");
    }

    @Override
    public void deleteGenre(String id) {
        genreDaoJdbc.deleteById(UUID.fromString(id));
        System.out.println("Жанр удален");
    }

    @Override
    public void deleteAuthor(String id) {
        authorDaoJdbc.deleteById(UUID.fromString(id));
        System.out.println("Автор удален");
    }

    @Override
    public void insertBook(String bookTitle,
                           Integer publicationYear,
                           Integer pagesCount,
                           String authorName,
                           String authorLastName,
                           String genreName) {
        Author author = checkAuthorAndGet(authorName, authorLastName);
        Genre genre = checkGenreAndGet(genreName);

        Book book = new Book(null, bookTitle, publicationYear, pagesCount,
                new HashSet<>(Collections.singleton(author)), new HashSet<>(Collections.singleton(genre)));
        bookDaoJdbc.insert(book);
        System.out.printf("Книга %s успешно добавлена", bookTitle);
    }

    @Override
    public void insertGenre(String name) {
        genreDaoJdbc.insert(new Genre(null, name));
        System.out.printf("Жанр %s успешно добавлен", name);
    }

    @Override
    public void insertAuthor(String name, String lastName) {
        authorDaoJdbc.insert(new Author(name, lastName));
        System.out.printf("Автор %s %s успешно добавлен", name, lastName);
    }

    @Override
    public void updateBook(String bookId, String bookTitle, String publicationYear, String pageCount) {
        Book book = bookDaoJdbc.findById(UUID.fromString(bookId));
        book.setTitle(bookTitle);
        book.setPublicationYear(Integer.valueOf(publicationYear));
        book.setPageCount(Integer.valueOf(pageCount));
        bookDaoJdbc.update(book);
        System.out.printf("Книга %s успешно обновлена", bookId);
    }



    private Author checkAuthorAndGet(String authorName, String authorLastName) {
        if ("Unknown".equals(authorName) || "Unknown".equals(authorLastName)) {
            return null;
        } else {
            Author author = authorDaoJdbc.findByNameAndLasName(authorName, authorLastName);
            if (Objects.isNull(author)) {
                UUID authorId = authorDaoJdbc.insert(new Author(authorName, authorLastName));
                return authorDaoJdbc.findById(authorId);
            } else {
                return author;
            }
        }
    }

    public Genre checkGenreAndGet(String name) {
        if ("Unknown".equals(name)) {
            return null;
        } else {
            Genre genre = genreDaoJdbc.findByName(name);
            if (Objects.isNull(genre)) {
                UUID genreId = genreDaoJdbc.insert(new Genre(null, name));
                return genreDaoJdbc.findById(genreId);
            } else {
                return genre;
            }
        }
    }
}
