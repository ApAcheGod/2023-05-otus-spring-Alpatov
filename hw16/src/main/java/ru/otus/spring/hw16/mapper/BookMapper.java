package ru.otus.spring.hw16.mapper;

import org.mapstruct.Mapper;
import ru.otus.spring.hw16.dto.BookDto;
import ru.otus.spring.hw16.entity.Book;

import java.util.Set;

@Mapper(config = LibraryMapperConfig.class, uses = AuthorMapper.class)
public interface BookMapper {

    BookDto toDto(Book book);

    Book toEntity(BookDto bookDto);

    Set<BookDto> toDto(Set<Book> books);

    Set<Book> toEntity(Set<BookDto> books);

}
