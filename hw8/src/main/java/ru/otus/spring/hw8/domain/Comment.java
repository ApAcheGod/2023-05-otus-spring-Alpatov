package ru.otus.spring.hw8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.spring.hw8.annotation.Sequence;

import java.util.Objects;

@Data
@Document(collection = "comment")
@Sequence("comment_sequence")
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends AbstractDocument {

    private String comment;

    @DBRef
    @ToString.Exclude
    private Book book;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
