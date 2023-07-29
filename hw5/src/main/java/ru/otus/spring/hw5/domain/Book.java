package ru.otus.spring.hw5.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Book {

    private UUID id;

    private String title;

    private Integer publicationYear;

    private Integer pageCount;

    @ToString.Exclude
    private Set<Author> authors = new HashSet<>();

    @ToString.Exclude
    private Set<Genre> genres = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals(id, book.id)
                && Objects.equals(title, book.title)
                && Objects.equals(publicationYear, book.publicationYear)
                && Objects.equals(pageCount, book.getPageCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
