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
public class Author2BookWriter implements ItemWriter<Book> {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void write(Chunk<? extends Book> chunk) throws Exception {
        chunk.forEach(this::insertAuthor2Book);
    }

    private void insertAuthor2Book(Book book) {
        String author2bookTemplate =
                "INSERT INTO AUTHOR2BOOK (AUTHOR_ID, BOOK_ID) VALUES " +
                        "((select postgres_id from TEMP_IDS_MAPPING WHERE mongo_id =:authorId), " +
                        "(select postgres_id from TEMP_IDS_MAPPING WHERE mongo_id =:bookId))";
        if (!book.getAuthors().isEmpty()) {
            namedParameterJdbcTemplate.batchUpdate(author2bookTemplate, getAuthorParams(book));
        }
    }

    private SqlParameterSource[] getAuthorParams(Book book) {
        return book.getAuthors().stream()
                .filter(Objects::nonNull)
                .map(author -> new MapSqlParameterSource()
                        .addValue("authorId", author.getId())
                        .addValue("bookId", book.getId()))
                .toArray(SqlParameterSource[]::new);
    }
}
