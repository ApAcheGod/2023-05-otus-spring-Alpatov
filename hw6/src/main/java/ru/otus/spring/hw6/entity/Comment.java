package ru.otus.spring.hw6.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "COMMENT")
@NamedEntityGraph(name = "comment-book-entity-graph",
        attributeNodes = {@NamedAttributeNode(value = "book")})
public class Comment {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    @Column(name = "COMMENT", nullable = false)
    private String comment;

    @Fetch(FetchMode.SELECT)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_ID")
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
        return Objects.equals(id, comment.id)
                && Objects.equals(this.comment, comment.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
