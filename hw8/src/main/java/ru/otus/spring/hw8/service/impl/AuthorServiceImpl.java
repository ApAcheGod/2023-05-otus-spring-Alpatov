package ru.otus.spring.hw8.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw8.domain.Author;
import ru.otus.spring.hw8.exception.ParentAssociationsException;
import ru.otus.spring.hw8.repository.AuthorRepository;
import ru.otus.spring.hw8.service.AuthorService;
import ru.otus.spring.hw8.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final BookService bookService;

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            if (bookService.existsByAuthorsContains(optionalAuthor.get())) {
                throw new ParentAssociationsException("Невозможно удалить автора, есть связанные сущности");
            } else {
                authorRepository.deleteById(id);
            }
        }
    }

    @Override
    public Optional<Author> findByNameAndLasName(String name, String lastName) {
        return authorRepository.findByNameAndLastName(name, lastName);
    }
}

