package ru.otus.spring.hw8.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.spring.hw8.annotation.Sequence;

import java.util.Objects;

@Data
@Sequence("genre_sequence")
@Document(collection = "genre")
@NoArgsConstructor
public class Genre extends AbstractDocument {

    private String name;

    public Genre(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Genre genre = (Genre) o;

        return Objects.equals(id, genre.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
