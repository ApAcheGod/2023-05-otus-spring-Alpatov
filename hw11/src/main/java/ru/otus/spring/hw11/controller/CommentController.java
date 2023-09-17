package ru.otus.spring.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw11.domain.Comment;
import ru.otus.spring.hw11.dto.CommentDto;
import ru.otus.spring.hw11.mapper.CommentMapper;
import ru.otus.spring.hw11.repository.BookRepository;
import ru.otus.spring.hw11.repository.CommentRepository;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentMapper commentMapper;

    @GetMapping("/api/comment/{id}")
    public Mono<ResponseEntity<CommentDto>> fendById(@PathVariable("id") String id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @GetMapping("/api/comment")
    public Flux<CommentDto> findAll() {
        return commentRepository.findAll().map(commentMapper::toDto);
    }

    @PostMapping("/api/comment")
    public Mono<ResponseEntity<CommentDto>> create(@RequestBody CommentDto commentDto) {
        return commentRepository.save(
                        new Comment(commentDto.getComment(),
                                bookRepository.findById(commentDto.getBookId()).block()))
                .map(commentMapper::toDto)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/api/comment")
    public Mono<ResponseEntity<CommentDto>> update(@RequestBody CommentDto commentDto) {
        return commentRepository.save(
                new Comment(commentDto.getId(), commentDto.getComment(),
                        bookRepository.findById(commentDto.getBookId()).block()))
                .map(commentMapper::toDto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/api/comment/{id}")
    public Mono<Void> deleteById(@PathVariable("id") String id) {
        return commentRepository.deleteById(id);
    }
}
