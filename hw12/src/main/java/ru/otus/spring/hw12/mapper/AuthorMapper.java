package ru.otus.spring.hw12.mapper;

import org.mapstruct.Mapper;
import ru.otus.spring.hw12.dto.AuthorDto;
import ru.otus.spring.hw12.entity.Author;

import java.util.Set;

@Mapper(config = LibraryMapperConfig.class)
public interface AuthorMapper {

    AuthorDto toDto(Author author);

    Author toEntity(AuthorDto authorDto);

    Set<AuthorDto> toDto(Set<Author> authors);

    Set<Author> toEntity(Set<AuthorDto> authors);

}
