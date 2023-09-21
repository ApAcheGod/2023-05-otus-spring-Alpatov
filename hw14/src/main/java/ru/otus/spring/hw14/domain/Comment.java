package ru.otus.spring.hw14.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Data
@Document(collection = "comment")
@NoArgsConstructor
public class Comment {

    private String id;

    private String comment;

    @ToString.Exclude
    private Book book;

    public Comment(String comment, Book book) {
        this.comment = comment;
        this.book = book;
    }

    public Comment(String id, String comment, Book book) {
        this.id = id;
        this.comment = comment;
        this.book = book;
    }

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
