package ru.otus.spring.hw6.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw6.entity.Author;
import ru.otus.spring.hw6.repository.AuthorRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Author> findById(UUID id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
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
        em.remove(em.find(Author.class, id));
    }

    @Override
    public Optional<Author> findByNameAndLasName(String name, String lastName) {
        TypedQuery<Author> query = em.createQuery("select a from Author a " +
                "where a.name = :name and a.lastName = :lastName",
                Author.class);
        query.setParameter("name", name);
        query.setParameter("lastName", lastName);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
