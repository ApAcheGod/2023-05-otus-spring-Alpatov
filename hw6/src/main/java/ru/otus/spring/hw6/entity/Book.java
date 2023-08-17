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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

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
@Table(name = "BOOK")
@NamedEntityGraph(name = "book-authors-genres-comments-entity-graph",
        attributeNodes = {
            @NamedAttributeNode(value = "authors"),
            @NamedAttributeNode(value = "genres"),
            @NamedAttributeNode(value = "comments")
})
@NamedEntityGraph(name = "book-authors-genres-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "authors"),
                @NamedAttributeNode(value = "genres")
        })
public class Book {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "PUBLICATION_YEAR")
    private Integer publicationYear;

    @Column(name = "PAGE_COUNT")
    private Integer pageCount;

    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "books", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Author> authors = new HashSet<>();

    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "BOOK2GENRE",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID"))
    private Set<Genre> genres = new HashSet<>();

    @ToString.Exclude
    @BatchSize(size = 10)
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "book", orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

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
