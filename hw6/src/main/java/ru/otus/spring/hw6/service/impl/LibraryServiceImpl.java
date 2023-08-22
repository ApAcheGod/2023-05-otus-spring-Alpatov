package ru.otus.spring.hw6.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw6.entity.Author;
import ru.otus.spring.hw6.entity.Book;
import ru.otus.spring.hw6.entity.Comment;
import ru.otus.spring.hw6.entity.Genre;
import ru.otus.spring.hw6.service.AuthorService;
import ru.otus.spring.hw6.service.BookService;
import ru.otus.spring.hw6.service.CommentService;
import ru.otus.spring.hw6.service.GenreService;
import ru.otus.spring.hw6.service.LibraryService;
import ru.otus.spring.hw6.utils.PrinterUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookService bookService;

    private final GenreService genreService;

    private final AuthorService authorService;

    private final CommentService commentService;

    @Override
    @Transactional
    public void get(String service, String id) {
        if ("Unknown".equals(service)) {
            System.out.println("Введите корректные параметры поиска");
        }
        switch (service) {
            case "a" -> getAuthor(id);
            case "b" -> getBook(id);
            case "g" -> getGenre(id);
            case "c" -> getComment(id);
        }
    }

    @Override
    public void delete(String service, String id) {
        if ("Unknown".equals(service)) {
            System.out.println("Введите корректные параметры поиска");
        }

        switch (service) {
            case "a" -> deleteAuthor(id);
            case "b" -> deleteBook(id);
            case "g" -> deleteGenre(id);
            case "c" -> deleteComment(id);
        }

    }

    @Override
    @Transactional
    public void insertBook(String bookTitle, Integer publicationYear,
                           Integer pagesCount, String authorName,
                           String authorLastName, String genreName) {
        Author author = checkAuthorAndGet(authorName, authorLastName);
        Genre genre = checkGenreAndGet(genreName);
        Book book = new Book();
        book.setTitle(bookTitle);
        book.setPublicationYear(publicationYear);
        book.setPageCount(pagesCount);

        if (Objects.nonNull(author)) {
            book.getAuthors().add(author);
            author.getBooks().add(book);
        }

        if (Objects.nonNull(genre)) {
            book.getGenres().add(genre);
        }

        bookService.insert(book);
        System.out.printf("Книга %s успешно добавлена", bookTitle);
    }

    @Override
    public void insertComment(String bookTitle, String commentText) {
        Optional<Book> optionalBook = bookService.findByTitle(bookTitle);
        if (optionalBook.isPresent()) {
            Comment comment = new Comment(null, commentText, optionalBook.get());
            optionalBook.get().getComments().add(comment);
            commentService.insert(comment);
        } else {
            System.out.printf("Книга %s не найдена", bookTitle);
        }
    }

    @Override
    @Transactional
    public void getComments(String bookTitle) {
        Optional<Book> optionalBook = bookService.findByTitle(bookTitle);
        optionalBook.ifPresentOrElse(
                book -> book.getComments().forEach(System.out::println),
                () -> System.out.printf("Книга %s не найдена", bookTitle));
    }

    @Override
    public void updateComment(String id, String commentText) {
        Optional<Comment> optionalComment = commentService.findById(UUID.fromString(id));
        if (optionalComment.isPresent()) {
            optionalComment.get().setComment(commentText);
            commentService.update(optionalComment.get());
        } else {
            System.out.println("Комментарий не найден");
        }

    }

    @Override
    public void insertGenre(String name) {
        genreService.insert(new Genre(null, name));
        System.out.printf("Жанр %s успешно добавлен", name);
    }

    @Override
    public void insertAuthor(String name, String lastName) {
        authorService.insert(new Author(name, lastName));
        System.out.printf("Автор %s %s успешно добавлен", name, lastName);
    }

    @Override
    public void updateBook(String bookId, String bookTitle, String publicationYear, String pageCount) {
        Optional<Book> optionalBook = bookService.findById(UUID.fromString(bookId));
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookTitle);
            book.setPublicationYear(Integer.valueOf(publicationYear));
            book.setPageCount(Integer.valueOf(pageCount));
            bookService.update(book);
            System.out.printf("Книга %s успешно обновлена", bookId);
        } else {
            System.out.println("Книга не найдена");
        }
    }



    private Author checkAuthorAndGet(String authorName, String authorLastName) {
        if ("Unknown".equals(authorName) || "Unknown".equals(authorLastName)) {
            return null;
        } else {
            Optional<Author> optionalAuthor = authorService.findByNameAndLasName(authorName, authorLastName);
            if (optionalAuthor.isPresent()) {
                return optionalAuthor.get();
            } else {
                Author author = new Author(authorName, authorLastName);
                authorService.insert(author);
                return author;
            }
        }
    }

    private Genre checkGenreAndGet(String name) {
        if ("Unknown".equals(name)) {
            return null;
        } else {
            Optional<Genre> optionalGenre = genreService.findByName(name);
            if (optionalGenre.isPresent()) {
                return optionalGenre.get();
            } else {
                Genre genre = new Genre(null, name);
                genreService.insert(genre);
                return genre;
            }
        }
    }

    private void getComment(String id) {
        if ("all".equals(id)) {
            commentService.findAll().stream().map(Comment::getComment).forEach(System.out::println);
        } else {
            Optional<Comment> optionalComment = commentService.findById(UUID.fromString(id));
            if (optionalComment.isPresent()) {
                System.out.println(optionalComment.get().getComment());
            } else {
                System.out.println("Комментарий не найден");
            }
        }
    }

    private void getBook(String id) {
        if ("all".equals(id)) {
            bookService.findAll().forEach(PrinterUtils::printBook);
        } else {
            Optional<Book> optionalBook = bookService.findById(UUID.fromString(id));
            if (optionalBook.isPresent()) {
                PrinterUtils.printBookWithComments(optionalBook.get());
            } else {
                System.out.println("Книга не найдена");
            }
        }
    }

    private void getGenre(String id) {
        if ("all".equals(id)) {
            System.out.println(genreService.findAll());
        } else {
            Optional<Genre> optionalGenre = genreService.findById(UUID.fromString(id));
            if (optionalGenre.isPresent()) {
                System.out.println(optionalGenre.get());
            } else {
                System.out.println("Жанр не найден");
            }
        }
    }

    private void getAuthor(String id) {
        if ("all".equals(id)) {
            System.out.println(authorService.findAll());
        } else {
            Optional<Author> optionalAuthor = authorService.findById(UUID.fromString(id));
            if (optionalAuthor.isPresent()) {
                System.out.println(optionalAuthor.get());
            } else {
                System.out.println("Автор не найден");
            }
        }
    }

    private void deleteBook(String id) {
        bookService.deleteById(UUID.fromString(id));
        System.out.println("Книга удалена");
    }


    private void deleteGenre(String id) {
        genreService.deleteById(UUID.fromString(id));
        System.out.println("Жанр удален");
    }


    private void deleteAuthor(String id) {
        authorService.deleteById(UUID.fromString(id));
        System.out.println("Автор удален");
    }

    private void deleteComment(String id) {
        commentService.deleteById(UUID.fromString(id));
        System.out.println("Комментарий удален");
    }
}
