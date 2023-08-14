package ru.otus.spring.hw6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw6.entity.Book;
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

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(UUID id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public void insert(Comment comment) {
        if (Objects.nonNull(comment.getId())) {
            throw new UnsupportedOperationException("Entity shouldn't contains ID");
        }
        commentRepository.insert(comment);
    }

    @Override
    public void update(Comment comment) {
        if (Objects.isNull(comment.getId())) {
            throw new UnsupportedOperationException("Entity should contains ID");
        }
        commentRepository.update(comment);
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteById(UUID id) {
        commentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAllByBook(Book book) {
        return commentRepository.findAllByBook(book);
    }
}
