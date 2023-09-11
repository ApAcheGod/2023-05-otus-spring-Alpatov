package ru.otus.spring.hw11.mapper;

import org.mapstruct.Mapper;
import ru.otus.spring.hw11.domain.Author;
import ru.otus.spring.hw11.dto.AuthorDto;

import java.util.Set;

@Mapper(config = LibraryMapperConfig.class)
public interface AuthorMapper {

    AuthorDto toDto(Author author);

    Author toEntity(AuthorDto authorDto);

    Set<AuthorDto> toDto(Set<Author> authors);

    Set<Author> toEntity(Set<AuthorDto> authors);

}
