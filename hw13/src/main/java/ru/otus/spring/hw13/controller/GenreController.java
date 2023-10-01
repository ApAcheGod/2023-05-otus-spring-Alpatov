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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.hw13.annotation.EntityNotFoundExceptionHandler;
import ru.otus.spring.hw13.entity.Genre;
import ru.otus.spring.hw13.service.GenreService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@EntityNotFoundExceptionHandler
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/api/genre")
    public List<Genre> getGenre() {
        return genreService.findAll();
    }

    @GetMapping("/api/genre/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable("id") UUID id) {
        Optional<Genre> optionalGenre = genreService.findById(id);
        if (optionalGenre.isPresent()) {
            return ResponseEntity.ok(optionalGenre.get());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @GetMapping(value = "/api/genre", params = "name")
    public ResponseEntity<Genre> getGenreByName(@RequestParam("name") String name) {
        Optional<Genre> optionalGenre = genreService.findByName(name);
        if (optionalGenre.isPresent()) {
            return ResponseEntity.ok(optionalGenre.get());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @PostMapping("/api/genre")
    public ResponseEntity<Genre> create(@RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.save(genre));
    }

    @PutMapping("/api/genre")
    public ResponseEntity<Genre> update(@RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.save(genre));
    }

    @DeleteMapping("/api/genre/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        genreService.deleteById(id);
    }

}
