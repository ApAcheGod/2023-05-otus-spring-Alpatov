package ru.otus.spring.hw13.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.hw13.annotation.EntityNotFoundExceptionHandler;
import ru.otus.spring.hw13.dto.CommentDto;
import ru.otus.spring.hw13.entity.Comment;
import ru.otus.spring.hw13.mapper.CommentMapper;
import ru.otus.spring.hw13.service.CommentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@EntityNotFoundExceptionHandler
public class CommentController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    @GetMapping("/api/comment/{id}")
    public ResponseEntity<CommentDto> fendById(@PathVariable("id") UUID id) {
        Optional<Comment> optionalComment = commentService.findById(id);
        if (optionalComment.isPresent()) {
            return ResponseEntity.ok(commentMapper.toDto(optionalComment.get()));
        } else {
            throw new EntityNotFoundException();
        }
    }

    @GetMapping("/api/comment")
    public List<CommentDto> findAll() {
        return commentService.findAll().stream().map(commentMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/api/comment")
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentMapper.toDto(commentService.save(commentDto)));
    }

    @PutMapping("/api/comment")
    public ResponseEntity<CommentDto> update(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentMapper.toDto(commentService.save(commentDto)));
    }

    @DeleteMapping("/api/comment/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        commentService.deleteById(id);
    }
}
