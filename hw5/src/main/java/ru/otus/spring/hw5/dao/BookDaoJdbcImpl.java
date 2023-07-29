package ru.otus.spring.hw5.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw5.domain.Author;
import ru.otus.spring.hw5.domain.Book;
import ru.otus.spring.hw5.domain.Genre;
import ru.otus.spring.hw5.utils.ResultSetUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbcImpl implements BookDaoJdbc {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Book findById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Book> resultList = namedParameterJdbcTemplate.query("""
                SELECT
                    B.ID AS BOOK_ID,
                    B.TITLE AS BOOK_TITLE,
                    B.PUBLICATION_YEAR AS PUBLICATION_YEAR,
                    B.PAGE_COUNT AS PAGE_COUNT,
                    A.ID as AUTHOR_ID,
                    A.NAME as AUTHOR_NAME,
                    A.LAST_NAME as AUTHOR_LAST_NAME,
                    G.ID as GENRE_ID,
                    G.NAME as GENRE_NAME
                FROM BOOK B
                    LEFT JOIN AUTHOR2BOOK A2B ON B.ID = A2B.BOOK_ID
                    LEFT JOIN AUTHOR A ON A2B.AUTHOR_ID = A.ID
                    LEFT JOIN BOOK2GENRE B2G ON B.ID = B2G.BOOK_ID
                    LEFT JOIN GENRE G ON B2G.GENRE_ID = G.ID
                WHERE B.ID = :id
                """, params, new BookDaoJdbcImpl.BookLeftJoin());
        return  resultList != null && resultList.size() == 1
                ? resultList.get(0)
                : null;
    }

    @Override
    public List<Book> findAll() {
        return namedParameterJdbcTemplate.query("""
                SELECT
                    B.ID AS BOOK_ID,
                    B.TITLE AS BOOK_TITLE,
                    B.PUBLICATION_YEAR AS PUBLICATION_YEAR,
                    B.PAGE_COUNT AS PAGE_COUNT,
                    A.ID as AUTHOR_ID,
                    A.NAME as AUTHOR_NAME,
                    A.LAST_NAME as AUTHOR_LAST_NAME,
                    G.ID as GENRE_ID,
                    G.NAME as GENRE_NAME
                FROM BOOK B
                    LEFT JOIN AUTHOR2BOOK A2B ON B.ID = A2B.BOOK_ID
                    LEFT JOIN AUTHOR A ON A2B.AUTHOR_ID = A.ID
                    LEFT JOIN BOOK2GENRE B2G ON B.ID = B2G.BOOK_ID
                    LEFT JOIN GENRE G ON B2G.GENRE_ID = G.ID
                """, new BookDaoJdbcImpl.BookLeftJoin());
    }

    @Override
    public void update(Book book) {
        namedParameterJdbcTemplate.update("""
                UPDATE BOOK SET TITLE = :title,
                PUBLICATION_YEAR = :publicationYear,
                PAGE_COUNT = :pageCount
                WHERE ID = :id
                """, getParams(book));
    }

    @Override
    public UUID insert(Book book) {
        String bookQuery;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        if (Objects.nonNull(book.getId())) {
            bookQuery = "INSERT INTO BOOK (ID, TITLE, PUBLICATION_YEAR, PAGE_COUNT) " +
                    "VALUES (:id, :title, :publicationYear, :pageCount)";
            mapSqlParameterSource.addValues(getParams(book));
        } else {
            bookQuery = "INSERT INTO BOOK (TITLE, PUBLICATION_YEAR, PAGE_COUNT) " +
                    "VALUES (:title, :publicationYear, :pageCount) ";
            mapSqlParameterSource.addValues(getParams(book));
        }

        namedParameterJdbcTemplate.update(bookQuery, mapSqlParameterSource, keyHolder);
        UUID bookId = (UUID) Objects.requireNonNull(keyHolder.getKeys()).get("id");
        book.setId(bookId);
        insertBook2Genre(book);
        insertAuthor2Book(book);
        return bookId;
    }

    private void insertBook2Genre(Book book) {
        String book2genreQuery = "INSERT INTO BOOK2GENRE (BOOK_ID, GENRE_ID) VALUES (:bookId, :genreId)";
        if (!book.getGenres().isEmpty()) {
            namedParameterJdbcTemplate.batchUpdate(book2genreQuery, getGenreParams(book));
        }
    }

    private void insertAuthor2Book(Book book) {
        String author2bookTemplate = "INSERT INTO AUTHOR2BOOK (AUTHOR_ID, BOOK_ID) VALUES (:authorId, :bookId)";
        if (!book.getAuthors().isEmpty()) {
            namedParameterJdbcTemplate.batchUpdate(author2bookTemplate, getAuthorParams(book));
        }
    }

    private SqlParameterSource[] getGenreParams(Book book) {
       return book.getGenres().stream()
               .filter(Objects::nonNull)
               .map(genre -> new MapSqlParameterSource()
                       .addValue("bookId", book.getId())
                       .addValue("genreId", genre.getId()))
               .toArray(SqlParameterSource[]::new);
    }

    private SqlParameterSource[] getAuthorParams(Book book) {
        return book.getAuthors().stream()
                .filter(Objects::nonNull)
                .map(author -> new MapSqlParameterSource()
                        .addValue("authorId", author.getId())
                        .addValue("bookId", book.getId()))
                .toArray(SqlParameterSource[]::new);
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcTemplate.update("DELETE FROM BOOK B WHERE B.ID = :id", params);
    }

    @Override
    public Book findByTitle(String title) {
        Map<String, Object> params = Collections.singletonMap("title", title);
        List<Book> resultList = namedParameterJdbcTemplate.query("""
                SELECT
                    B.ID AS BOOK_ID,
                    B.TITLE AS BOOK_TITLE,
                    B.PUBLICATION_YEAR AS PUBLICATION_YEAR,
                    B.PAGE_COUNT AS PAGE_COUNT,
                    A.ID as AUTHOR_ID,
                    A.NAME as AUTHOR_NAME,
                    A.LAST_NAME as AUTHOR_LAST_NAME,
                    G.ID as GENRE_ID,
                    G.NAME as GENRE_NAME
                FROM BOOK B
                    LEFT JOIN AUTHOR2BOOK A2B ON B.ID = A2B.BOOK_ID
                    LEFT JOIN AUTHOR A ON A2B.AUTHOR_ID = A.ID
                    LEFT JOIN BOOK2GENRE B2G ON B.ID = B2G.BOOK_ID
                    LEFT JOIN GENRE G ON B2G.GENRE_ID = G.ID
                WHERE B.TITLE = :title
                """, params, new BookLeftJoin());
        return  resultList != null && resultList.size() == 1
                ? resultList.get(0)
                : null;
    }

    private Map<String, Object> getParams(Book book) {
        if (Objects.nonNull(book.getId())) {
            return Map.of("id", book.getId(), "title", book.getTitle(),
                    "publicationYear", book.getPublicationYear(), "pageCount", book.getPageCount());
        } else {
            return Map.of("title", book.getTitle(), "publicationYear",
                    book.getPublicationYear(), "pageCount", book.getPageCount());
        }
    }

    private static class BookLeftJoin implements ResultSetExtractor<List<Book>> {

        @Override
        public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<UUID, Book> books = new HashMap<>();
            while (rs.next()) {
                Author author = ResultSetUtils.getAuthor(rs);
                Genre genre = ResultSetUtils.getGenre(rs);
                Book book = ResultSetUtils.getBook(rs);
                ResultSetUtils.fillBookAssociations(books, book, author, genre);
            }
            return new ArrayList<>(books.values());
        }
    }
}
