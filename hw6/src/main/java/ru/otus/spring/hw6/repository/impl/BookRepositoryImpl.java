package ru.otus.spring.hw6.repository.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw6.entity.Book;
import ru.otus.spring.hw6.repository.BookRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Component
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Book> findById(UUID id) {
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b " +
                "left join fetch b.authors " +
//                "left join fetch b.comments " +
                "left join fetch b.genres " +
                "where b.id = :id", Book.class);
        query.setParameter("id", id);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-authors-genres-entity-graph");
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b " +
                "left join fetch b.authors " +
                "left join fetch b.genres", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public void save(Book book) {
        if (Objects.isNull(book.getId())) {
            insert(book);
        } else {
            update(book);
        }
    }

    @Override
    public void insert(Book book) {
        em.persist(book);
    }

    @Override
    public void deleteById(UUID id) {
        em.remove(em.find(Book.class, id));
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b " +
                "left join fetch b.authors " +
                "left join fetch b.genres " +
                "where b.title = :title", Book.class);
        query.setParameter("title", title);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
