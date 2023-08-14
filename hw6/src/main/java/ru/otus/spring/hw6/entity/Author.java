package ru.otus.spring.hw6.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "AUTHOR")
@NamedEntityGraph(name = "author-books-entity-graph",
        attributeNodes = {@NamedAttributeNode(value = "books")})
public class Author {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @ToString.Exclude
    @BatchSize(size = 10)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "AUTHOR2BOOK",
            joinColumns = @JoinColumn(name = "AUTHOR_ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID"))
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
