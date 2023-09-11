package ru.otus.spring.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw11.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Mono<Void> deleteAllByBookId(String id);

    Flux<Comment> findAllByBookId(String bookId);
}
