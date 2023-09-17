package ru.otus.spring.hw11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.hw11.domain.Genre;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private String id;

    private String title;

    private Integer publicationYear;

    private Integer pageCount;

    private Set<AuthorDto> authors;

    private Set<Genre> genres;

}
