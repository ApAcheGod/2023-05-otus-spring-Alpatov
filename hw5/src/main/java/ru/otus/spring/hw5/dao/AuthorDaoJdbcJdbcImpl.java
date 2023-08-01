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
public class AuthorDaoJdbcJdbcImpl implements AuthorDaoJdbc {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Author findById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Author> resultList = namedParameterJdbcTemplate.query("""
                SELECT A.ID AS AUTHOR_ID,
                       A.NAME AS AUTHOR_NAME,
                       A.LAST_NAME AS AUTHOR_LAST_NAME,
                       B.ID AS BOOK_ID,
                       B.TITLE AS BOOK_TITLE,
                       B.PUBLICATION_YEAR AS PUBLICATION_YEAR,
                       B.PAGE_COUNT AS PAGE_COUNT,
                       G.ID AS GENRE_ID,
                       G.NAME AS GENRE_NAME
                FROM AUTHOR A
                    LEFT JOIN AUTHOR2BOOK A2B ON A.ID = A2B.AUTHOR_ID
                    LEFT JOIN BOOK B ON A2B.BOOK_ID = B.ID
                    LEFT JOIN BOOK2GENRE B2G ON B.ID = B2G.BOOK_ID
                    LEFT JOIN GENRE G ON B2G.GENRE_ID = G.ID
                WHERE A.ID = :id
                """, params, new AuthorDaoJdbcJdbcImpl.AuthorLeftJoin());
        return  resultList != null && resultList.size() == 1
                ? resultList.get(0)
                : null;
    }

    @Override
    public List<Author> findAll() {
        return namedParameterJdbcTemplate.query("""
                SELECT A.ID AS AUTHOR_ID,
                       A.NAME AS AUTHOR_NAME,
                       A.LAST_NAME AS AUTHOR_LAST_NAME,
                       B.ID AS BOOK_ID,
                       B.TITLE AS BOOK_TITLE,
                       B.PUBLICATION_YEAR AS PUBLICATION_YEAR,
                       B.PAGE_COUNT AS PAGE_COUNT,
                       G.ID AS GENRE_ID,
                       G.NAME AS GENRE_NAME
                FROM AUTHOR A
                    LEFT JOIN AUTHOR2BOOK A2B ON A.ID = A2B.AUTHOR_ID
                    LEFT JOIN BOOK B ON A2B.BOOK_ID = B.ID
                    LEFT JOIN BOOK2GENRE B2G ON B.ID = B2G.BOOK_ID
                    LEFT JOIN GENRE G ON B2G.GENRE_ID = G.ID
                """, new AuthorDaoJdbcJdbcImpl.AuthorLeftJoin());
    }

    @Override
    public UUID insert(Author author) {
        String authorQuery;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        if (Objects.nonNull(author.getId())) {
            authorQuery = "INSERT INTO AUTHOR (ID, NAME, LAST_NAME) VALUES (:id, :name, :lastName)";
            mapSqlParameterSource.addValues(getParams(author));

        } else {
            authorQuery = "INSERT INTO AUTHOR (NAME, LAST_NAME) VALUES (:name, :lastName)";
            mapSqlParameterSource.addValues(getParams(author));
        }

        namedParameterJdbcTemplate.update(authorQuery, mapSqlParameterSource, keyHolder);
        UUID uuid = (UUID) Objects.requireNonNull(keyHolder.getKeys()).get("id");
        insertAuthor2Book(author);
        return uuid;
    }

    @Override
    public void update(Author author) {
        Map<String, Object> params = Map.of(
                "id", author.getId(),
                "name", author.getName(),
                "lastName", author.getLastName());
        namedParameterJdbcTemplate.update(
                "UPDATE AUTHOR SET NAME = :name, LAST_NAME = :lastName  WHERE ID = :id",
                params);
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcTemplate.update("DELETE FROM AUTHOR WHERE ID = :id", params);
    }

    @Override
    public Author findByNameAndLasName(String name, String lastName) {
        Map<String, Object> params = Map.of("name", name, "lastName", lastName);
        List<Author> resultList = namedParameterJdbcTemplate.query("""
                SELECT A.ID AS AUTHOR_ID,
                       A.NAME AS AUTHOR_NAME,
                       A.LAST_NAME AS AUTHOR_LAST_NAME,
                       B.ID AS BOOK_ID,
                       B.TITLE AS BOOK_TITLE,
                       B.PUBLICATION_YEAR AS PUBLICATION_YEAR,
                       B.PAGE_COUNT AS PAGE_COUNT,
                       G.ID AS GENRE_ID,
                       G.NAME AS GENRE_NAME
                FROM AUTHOR A
                    LEFT JOIN AUTHOR2BOOK A2B ON A.ID = A2B.AUTHOR_ID
                    LEFT JOIN BOOK B ON A2B.BOOK_ID = B.ID
                    LEFT JOIN BOOK2GENRE B2G ON B.ID = B2G.BOOK_ID
                    LEFT JOIN GENRE G ON B2G.GENRE_ID = G.ID
                WHERE A.NAME = :name AND A.LAST_NAME = :lastName
                """, params, new AuthorLeftJoin());
        return  resultList != null && resultList.size() == 1
                ? resultList.get(0)
                : null;
    }

    private void insertAuthor2Book(Author author) {
        String author2bookTemplate = "INSERT INTO AUTHOR2BOOK (AUTHOR_ID, BOOK_ID) VALUES (:authorId, :bookId)";
        if (!author.getBooks().isEmpty()) {
            namedParameterJdbcTemplate.batchUpdate(author2bookTemplate, getBookParams(author));
        }
    }

    private SqlParameterSource[] getBookParams(Author author) {
        return author.getBooks().stream()
                .filter(Objects::nonNull)
                .map(book -> new MapSqlParameterSource()
                        .addValue("authorId", author.getId())
                        .addValue("bookId", book.getId()))
                .toArray(SqlParameterSource[]::new);
    }

    private Map<String, Object> getParams(Author author) {
        if (Objects.nonNull(author.getId())) {
            return Map.of("id", author.getId(), "name", author.getName(), "lastName", author.getLastName());
        } else {
            return Map.of("name", author.getName(), "lastName", author.getLastName());
        }
    }

    private static class AuthorLeftJoin implements ResultSetExtractor<List<Author>> {

        @Override
        public List<Author> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<UUID, Author> authors = new HashMap<>();
            Map<UUID, Book> books = new HashMap<>();
            while (rs.next()) {
                Genre genre = ResultSetUtils.getGenre(rs);
                Author author = ResultSetUtils.getAuthor(rs);
                Book book = ResultSetUtils.getBook(rs);
                ResultSetUtils.fillAuthorAssociations(authors, books, author, book, genre);
            }
            return new ArrayList<>(authors.values());
        }
    }
}
