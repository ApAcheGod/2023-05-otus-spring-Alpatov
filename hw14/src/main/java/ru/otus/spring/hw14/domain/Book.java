package ru.otus.spring.hw14.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Document(collection = "book")
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String id;

    private String title;

    private Integer publicationYear;

    private Integer pageCount;

    @ToString.Exclude
    private List<Author> authors = new ArrayList<>();

    @ToString.Exclude
    private List<Genre> genres = new ArrayList<>();

    public Book(String title, Integer publicationYear, Integer pageCount, List<Author> authors, List<Genre> genres) {
        this.title = title;
        this.publicationYear = publicationYear;
        this.pageCount = pageCount;
        this.authors = authors;
        this.genres = genres;
    }

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
