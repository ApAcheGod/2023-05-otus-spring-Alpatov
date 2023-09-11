package ru.otus.spring.hw11.mapper;

import org.mapstruct.Mapper;
import ru.otus.spring.hw11.domain.Book;
import ru.otus.spring.hw11.dto.BookDto;

import java.util.Set;

@Mapper(config = LibraryMapperConfig.class, uses = AuthorMapper.class)
public interface BookMapper {

    BookDto toDto(Book book);

    Book toEntity(BookDto bookDto);

    Set<BookDto> toDto(Set<Book> books);

    Set<Book> toEntity(Set<BookDto> books);

}
