package ru.otus.spring.hw8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.spring.hw8.annotation.CascadeSave;
import ru.otus.spring.hw8.annotation.Sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Document(collection = "book")
@Sequence("comment_book")
@NoArgsConstructor
@AllArgsConstructor
public class Book extends AbstractDocument {

    private String title;

    private Integer publicationYear;

    private Integer pageCount;

    @DBRef
    @CascadeSave
    @ToString.Exclude
    private List<Author> authors = new ArrayList<>();

    @DBRef
    @CascadeSave
    @ToString.Exclude
    private List<Genre> genres = new ArrayList<>();

    @DBRef
    @CascadeSave
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
