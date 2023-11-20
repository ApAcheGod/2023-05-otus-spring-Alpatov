package ru.otus.spring.hw18.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw18.dto.CommentDto;
import ru.otus.spring.hw18.entity.Comment;
import ru.otus.spring.hw18.repository.CommentRepository;
import ru.otus.spring.hw18.service.CommentService;
import ru.otus.spring.hw18.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@CircuitBreaker(name = "commentServiceCircuitBreaker")
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> findById(UUID id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment save(CommentDto commentDto) {
        return commentRepository.save(
                new Comment(commentDto.getId(),
                commentDto.getComment(),
                bookRepository.getById(commentDto.getBookId())));
    }

    @Override
    public void deleteById(UUID id) {
        commentRepository.deleteById(id);
    }
}
