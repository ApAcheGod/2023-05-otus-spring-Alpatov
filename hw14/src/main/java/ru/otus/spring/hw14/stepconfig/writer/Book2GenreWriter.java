package ru.otus.spring.hw14.stepconfig.writer;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw14.domain.Book;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Book2GenreWriter implements ItemWriter<Book> {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void write(Chunk<? extends Book> chunk) throws Exception {
        chunk.forEach(this::insertBook2Genre);
    }

    private void insertBook2Genre(Book book) {
        String author2bookTemplate =
                "INSERT INTO BOOK2GENRE (BOOK_ID, GENRE_ID) VALUES " +
                        "((select postgres_id from TEMP_IDS_MAPPING WHERE mongo_id =:bookId), " +
                        "(select postgres_id from TEMP_IDS_MAPPING WHERE mongo_id =:genreId))";
        if (!book.getGenres().isEmpty()) {
            namedParameterJdbcTemplate.batchUpdate(author2bookTemplate, getAuthorParams(book));
        }
    }

    private SqlParameterSource[] getAuthorParams(Book book) {
        return book.getGenres().stream()
                .filter(Objects::nonNull)
                .map(genre -> new MapSqlParameterSource()
                        .addValue("genreId", genre.getId())
                        .addValue("bookId", book.getId()))
                .toArray(SqlParameterSource[]::new);
    }
}
