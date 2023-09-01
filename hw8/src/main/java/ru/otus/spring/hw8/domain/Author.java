package ru.otus.spring.hw8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.spring.hw8.annotation.Sequence;

import java.util.Objects;

@Data
@Document(collection = "author")
@Sequence("comment_author")
@NoArgsConstructor
@AllArgsConstructor
public class Author extends AbstractDocument {

    private String name;

    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
