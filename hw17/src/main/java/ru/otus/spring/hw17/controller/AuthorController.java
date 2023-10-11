package ru.otus.spring.hw17.controller;

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
import ru.otus.spring.hw17.annotation.EntityNotFoundExceptionHandler;
import ru.otus.spring.hw17.dto.AuthorDto;
import ru.otus.spring.hw17.entity.Author;
import ru.otus.spring.hw17.mapper.AuthorMapper;
import ru.otus.spring.hw17.service.AuthorService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@EntityNotFoundExceptionHandler
public class AuthorController {

    private final AuthorService authorService;

    private final AuthorMapper authorMapper;

    @GetMapping("/api/author")
    public List<AuthorDto> getAuthors() {
         return authorService.findAll()
                .stream()
                .map(authorMapper::toDto)
                .toList();
    }

    @GetMapping("/api/author/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") UUID id) {
        Optional<Author> optionalAuthor = authorService.findById(id);
        if (optionalAuthor.isPresent()) {
            return ResponseEntity.ok(authorMapper.toDto(optionalAuthor.get()));
        } else {
            throw new EntityNotFoundException();
        }
    }

    @PostMapping("/api/author")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        authorService.save(author);
        return ResponseEntity.ok(authorMapper.toDto(author));
    }


    @PutMapping("/api/author")
    public void updateAuthor(@RequestBody AuthorDto authorDto) {
        authorService.save(authorMapper.toEntity(authorDto));
    }

    @DeleteMapping("/api/author/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        authorService.deleteById(id);
    }
}
