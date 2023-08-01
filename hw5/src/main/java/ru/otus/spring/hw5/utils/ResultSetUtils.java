package ru.otus.spring.hw5.utils;

import ru.otus.spring.hw5.domain.Author;
import ru.otus.spring.hw5.domain.Book;
import ru.otus.spring.hw5.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class ResultSetUtils {

    private ResultSetUtils() {

    }

    public static Genre getGenre(ResultSet rs) throws SQLException {
        UUID genreId = (UUID) rs.getObject("GENRE_ID");
        String genreName = rs.getString("GENRE_NAME");
        return new Genre(genreId, genreName);
    }

    public static Author getAuthor(ResultSet rs) throws SQLException {
        UUID authorId = (UUID) rs.getObject("AUTHOR_ID");
        String authorName = rs.getString("AUTHOR_NAME");
        String authorLstName = rs.getString("AUTHOR_LAST_NAME");
        return new Author(authorId, authorName, authorLstName, new HashSet<>());
    }

    public static Book getBook(ResultSet rs) throws SQLException {
        UUID bookId = (UUID) rs.getObject("BOOK_ID");
        String bookTitle = rs.getString("BOOK_TITLE");
        Integer publicationYear = rs.getInt("PUBLICATION_YEAR");
        Integer pageCount = rs.getInt("PAGE_COUNT");
        return new Book(bookId, bookTitle, publicationYear, pageCount, new HashSet<>(), new HashSet<>());
    }

    public static void fillBookAssociations(Map<UUID, Book> books, Book book, Author author, Genre genre) {
        if (books.containsKey(book.getId())) {
            books.get(book.getId()).getAuthors().add(author);
            books.get(book.getId()).getGenres().add(genre);
        } else {
            book.getGenres().add(genre);
            book.getAuthors().add(author);
            author.getBooks().add(book);
            books.put(book.getId(), book);
        }
    }

    public static void fillAuthorAssociations(Map<UUID, Author> authors,
                                              Map<UUID, Book> books,
                                              Author author, Book book, Genre genre) {
        fillBookAssociations(books, book, author, genre);
        if (authors.containsKey(author.getId())) {
            authors.get(author.getId()).getBooks().add(books.get(book.getId()));
        } else {
            author.getBooks().add(books.get(book.getId()));
            authors.put(author.getId(), author);
        }
    }
}
