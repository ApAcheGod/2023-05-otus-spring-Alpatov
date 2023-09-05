package ru.otus.spring.hw9.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.hw9.entity.Genre;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private UUID id;

    private String title;

    private Integer publicationYear;

    private Integer pageCount;

    private Set<AuthorDto> authors;

    private Set<Genre> genres;

}
