package ru.otus.spring.hw6.repository.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw6.dto.AuthorDto;
import ru.otus.spring.hw6.entity.Author;
import ru.otus.spring.hw6.repository.AuthorRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Author> findById(UUID id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("author-books-entity-graph");
        TypedQuery<Author> query = em.createQuery(
                "select distinct a from Author a left join fetch a.books " +
                        "where a.id = :id", Author.class);
        query.setHint(FETCH.getKey(), entityGraph);
        query.setParameter("id", id);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AuthorDto> findDtoById(UUID id) {
        TypedQuery<AuthorDto> query = em.createQuery(
                "select new ru.otus.spring.hw6.dto.AuthorDto(a.id, a.name, a.lastName) " +
                        "from Author a where a.id = :id", AuthorDto.class);
        query.setParameter("id", id);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("author-books-entity-graph");
        TypedQuery<Author> query = em.createQuery(
                "select distinct a from Author a left join fetch a.books", Author.class);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public List<AuthorDto> findAllDto() {
        TypedQuery<AuthorDto> query = em.createQuery(
                "select new ru.otus.spring.hw6.dto.AuthorDto(a.id, a.name, a.lastName) from Author a",
                AuthorDto.class);
        return query.getResultList();
    }

    @Override
    public void insert(Author author) {
        em.persist(author);
    }

    @Override
    public void update(Author author) {
        em.merge(author);
    }

    @Override
    public void save(Author author) {
        if (Objects.isNull(author.getId())) {
            insert(author);
        } else {
            update(author);
        }
    }

    @Override
    public void deleteById(UUID id) {
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Author> findByNameAndLasName(String name, String lastName) {
        EntityGraph<?> entityGraph = em.getEntityGraph("author-books-entity-graph");
        TypedQuery<Author> query = em.createQuery("select a from Author a " +
                "where a.name = :name and a.lastName = :lastName",
                Author.class);
        query.setParameter("name", name);
        query.setParameter("lastName", lastName);
        query.setHint(FETCH.getKey(), entityGraph);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<AuthorDto> findDtoByNameAndLasName(String name, String lastName) {
        TypedQuery<AuthorDto> query = em.createQuery("select " +
                        "new ru.otus.spring.hw6.dto.AuthorDto(a.id, a.name, a.lastName) from Author a " +
                        "where a.name = :name and a.lastName = :lastName",
                AuthorDto.class);
        query.setParameter("name", name);
        query.setParameter("lastName", lastName);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
