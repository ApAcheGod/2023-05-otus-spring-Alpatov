package ru.otus.spring.hw11.controller;

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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw11.domain.Genre;
import ru.otus.spring.hw11.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping("/api/genre")
    public Flux<Genre> getGenre() {
        return genreRepository.findAll();
    }

    @GetMapping("/api/genre/{id}")
    public Mono<ResponseEntity<Genre>> getGenreById(@PathVariable("id") String id) {
       return genreRepository.findById(id)
               .map(ResponseEntity::ok)
               .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @GetMapping(value = "/api/genre", params = "name")
    public Mono<ResponseEntity<Genre>> getGenreByName(@RequestParam("name") String name) {
        return genreRepository.findByName(name)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @PostMapping("/api/genre")
    public Mono<ResponseEntity<Genre>> create(@RequestBody Genre genre) {
        return genreRepository.save(genre).map(ResponseEntity::ok);
    }

    @PutMapping("/api/genre")
    public Mono<ResponseEntity<Genre>> update(@RequestBody Genre genre) {
        return genreRepository.save(genre).map(ResponseEntity::ok);
    }

    @DeleteMapping("/api/genre/{id}")
    public Mono<Void> deleteById(@PathVariable("id") String id) {
        return genreRepository.deleteById(id);
    }

}
