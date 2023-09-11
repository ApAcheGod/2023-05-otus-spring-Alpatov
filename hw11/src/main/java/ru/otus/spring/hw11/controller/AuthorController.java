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
import ru.otus.spring.hw11.dto.AuthorDto;
import ru.otus.spring.hw11.mapper.AuthorMapper;
import ru.otus.spring.hw11.repository.AuthorRepository;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @GetMapping("/api/author")
    public Flux<AuthorDto> getAuthors() {
         return authorRepository.findAll()
                 .map(authorMapper::toDto);
    }

    @GetMapping("/api/author/{id}")
    public Mono<ResponseEntity<AuthorDto>> getAuthorById(@PathVariable("id") String id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @PostMapping(value = "/api/author")
    public Mono<ResponseEntity<AuthorDto>> createAuthor(@RequestBody AuthorDto authorDto) {
        return authorRepository.save(authorMapper.toEntity(authorDto))
                .map(authorMapper::toDto)
                .map(ResponseEntity::ok);
    }


    @PutMapping("/api/author")
    public Mono<ResponseEntity<AuthorDto>> updateAuthor(@RequestBody AuthorDto authorDto) {
        return authorRepository.save(authorMapper.toEntity(authorDto))
                .map(authorMapper::toDto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/api/author/{id}")
    public Mono<Void> deleteById(@PathVariable("id") String id) {
        return authorRepository.deleteById(id);
    }
}
