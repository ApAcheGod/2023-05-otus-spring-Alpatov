package ru.otus.spring.hw6.repository.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw6.entity.Comment;
import ru.otus.spring.hw6.repository.CommentRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Component
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Comment> findById(UUID id) {
        TypedQuery<Comment> query = em.createQuery("select distinct c from Comment c join fetch c.book " +
                        "where c.id = :id", Comment.class);
        query.setParameter("id", id);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Comment> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("comment-book-entity-graph");
        TypedQuery<Comment> query = em.createQuery("select distinct c from Comment c join fetch c.book",
                Comment.class);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public void insert(Comment comment) {
        em.persist(comment);
    }

    @Override
    public void update(Comment comment) {
        em.merge(comment);
    }

    @Override
    public void save(Comment comment) {
        if (Objects.isNull(comment.getId())) {
            insert(comment);
        } else {
            update(comment);
        }
    }

    @Override
    public void deleteById(UUID id) {
        em.remove(em.find(Comment.class, id));
    }

}
