package ru.otus.spring.hw6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw6.entity.Comment;
import ru.otus.spring.hw6.repository.CommentRepository;
import ru.otus.spring.hw6.service.CommentService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Optional<Comment> findById(UUID id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional
    public void insert(Comment comment) {
        if (Objects.nonNull(comment.getId())) {
            throw new UnsupportedOperationException("Entity shouldn't contains ID");
        }
        commentRepository.insert(comment);
    }

    @Override
    @Transactional
    public void update(Comment comment) {
        if (Objects.isNull(comment.getId())) {
            throw new UnsupportedOperationException("Entity should contains ID");
        }
        commentRepository.update(comment);
    }

    @Override
    @Transactional
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        commentRepository.deleteById(id);
    }
}
