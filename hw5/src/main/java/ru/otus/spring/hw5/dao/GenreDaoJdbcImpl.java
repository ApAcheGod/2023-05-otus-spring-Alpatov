package ru.otus.spring.hw5.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw5.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbcImpl implements GenreDaoJdbc {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Genre findById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcTemplate.queryForObject(
                "SELECT ID AS GENRE_ID, NAME as GENRE_NAME FROM GENRE WHERE ID = :id",
                params, new GenreDaoJdbcImpl.GenreMapper());
    }

    @Override
    public List<Genre> findAll() {
        return namedParameterJdbcTemplate.query(
                "SELECT ID as GENRE_ID, NAME as GENRE_NAME FROM GENRE",
                new GenreDaoJdbcImpl.GenreMapper());
    }

    @Override
    public UUID insert(Genre genre) {
        String query;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        if (Objects.nonNull(genre.getId())) {
            query = "INSERT INTO GENRE (ID, NAME) VALUES (:id, :name)";
            mapSqlParameterSource.addValues(getParams(genre));
        } else {
            query = "INSERT INTO GENRE (NAME) VALUES (:name)";
            mapSqlParameterSource.addValues(getParams(genre));
        }
        namedParameterJdbcTemplate.update(query, mapSqlParameterSource, keyHolder);
        return (UUID) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    @Override
    public void update(Genre genre) {
        namedParameterJdbcTemplate.update("UPDATE GENRE SET NAME = :name  WHERE ID = :id", getParams(genre));
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcTemplate.update("DELETE FROM GENRE WHERE ID = :id", params);
    }

    @Override
    public Genre findByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return namedParameterJdbcTemplate.queryForObject(
                "SELECT ID AS GENRE_ID, NAME as GENRE_NAME FROM GENRE WHERE NAME = :name",
                params, new GenreDaoJdbcImpl.GenreMapper());
    }

    private Map<String, Object> getParams(Genre genre) {
        if (Objects.nonNull(genre.getId())) {
            return Map.of("id", genre.getId(), "name", genre.getName());
        } else {
            return Map.of("name", genre.getName());
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre((UUID) rs.getObject("GENRE_ID"), rs.getString("GENRE_NAME"));
        }
    }
}
