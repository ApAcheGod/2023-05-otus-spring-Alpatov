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
public class Author {

    private UUID id;

    private String name;

    private String lastName;

    @ToString.Exclude
    private Set<Book> books = new HashSet<>();

    public Author(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return Objects.equals(id, author.id)
                && Objects.equals(name, author.name)
                && Objects.equals(lastName, author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
