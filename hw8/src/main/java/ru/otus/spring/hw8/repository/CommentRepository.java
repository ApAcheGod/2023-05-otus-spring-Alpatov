package ru.otus.spring.hw8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw8.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, Long> {

    void deleteAllByBookId(Long id);
}
