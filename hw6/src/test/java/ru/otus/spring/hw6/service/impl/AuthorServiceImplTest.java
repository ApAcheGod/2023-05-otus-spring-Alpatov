package ru.otus.spring.hw6.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.hw6.entity.Author;
import ru.otus.spring.hw6.repository.AuthorRepository;
import ru.otus.spring.hw6.service.AuthorService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = AuthorServiceImpl.class)
@ExtendWith(SpringExtension.class)
class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void findById() {
        authorService.findById(any());
        verify(authorRepository, times(1)).findById(any());

    }

    @Test
    void findAll() {
        authorService.findAll();
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void insert() {
        Author author = new Author();
        authorService.insert(author);
        verify(authorRepository, times(1)).insert(author);

        author.setId(UUID.randomUUID());
        assertThatThrownBy(() -> authorService.insert(author))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void update() {
        Author author = new Author();
        author.setId(UUID.randomUUID());
        authorService.update(author);
        verify(authorRepository, times(1)).update(author);

        author.setId(null);
        assertThatThrownBy(() -> authorService.update(author))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void save() {
        authorService.save(any());
        verify(authorRepository, times(1)).save(any());
    }

    @Test
    void deleteById() {
        authorService.deleteById(any());
        verify(authorRepository, times(1)).deleteById(any());
    }

    @Test
    void findByNameAndLasName() {
        authorService.findByNameAndLasName(anyString(), anyString());
        verify(authorRepository, times(1)).findByNameAndLasName(anyString(), anyString());
    }
}