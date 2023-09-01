package ru.otus.spring.hw8.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw8.domain.Author;
import ru.otus.spring.hw8.domain.Book;
import ru.otus.spring.hw8.domain.Comment;
import ru.otus.spring.hw8.domain.Genre;
import ru.otus.spring.hw8.service.AuthorService;
import ru.otus.spring.hw8.service.BookService;
import ru.otus.spring.hw8.service.CommentService;
import ru.otus.spring.hw8.service.GenreService;
import ru.otus.spring.hw8.service.LibraryService;
import ru.otus.spring.hw8.utils.PrinterUtils;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookService bookService;

    private final GenreService genreService;

    private final AuthorService authorService;

    private final CommentService commentService;

    @Override
    @Transactional(readOnly = true)
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
        }

        if (Objects.nonNull(genre)) {
            book.getGenres().add(genre);
        }

        bookService.save(book);
        System.out.printf("Книга %s успешно добавлена", bookTitle);
    }

    @Override
    public void insertComment(String bookTitle, String commentText) {
        Optional<Book> optionalBook = bookService.findByTitle(bookTitle);
        if (optionalBook.isPresent()) {
            Comment comment = new Comment(commentText, optionalBook.get());
            optionalBook.get().getComments().add(comment);
//            commentService.save(comment);
            bookService.save(optionalBook.get());
        } else {
            System.out.printf("Книга %s не найдена", bookTitle);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void getComments(String bookTitle) {
        Optional<Book> optionalBook = bookService.findByTitle(bookTitle);
        optionalBook.ifPresentOrElse(
                book -> book.getComments().forEach(System.out::println),
                () -> System.out.printf("Книга %s не найдена", bookTitle));
    }

    @Override
    public void updateComment(String id, String commentText) {
        Optional<Comment> optionalComment = commentService.findById(Long.valueOf(id));
        if (optionalComment.isPresent()) {
            optionalComment.get().setComment(commentText);
            commentService.save(optionalComment.get());
        } else {
            System.out.println("Комментарий не найден");
        }

    }

    @Override
    public void insertGenre(String name) {
        genreService.save(new Genre(name));
        System.out.printf("Жанр %s успешно добавлен", name);
    }

    @Override
    public void insertAuthor(String name, String lastName) {
        authorService.save(new Author(name, lastName));
        System.out.printf("Автор %s %s успешно добавлен", name, lastName);
    }

    @Override
    public void updateBook(String bookId, String bookTitle, String publicationYear, String pageCount) {
        Optional<Book> optionalBook = bookService.findById(Long.valueOf(bookId));
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookTitle);
            book.setPublicationYear(Integer.valueOf(publicationYear));
            book.setPageCount(Integer.valueOf(pageCount));
            bookService.save(book);
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
            return optionalAuthor.orElseGet(() -> new Author(authorName, authorLastName));
        }
    }

    private Genre checkGenreAndGet(String name) {
        if ("Unknown".equals(name)) {
            return null;
        } else {
            Optional<Genre> optionalGenre = genreService.findByName(name);
            return optionalGenre.orElseGet(() -> new Genre(name));
        }
    }

    private void getComment(String id) {
        if ("all".equals(id)) {
            commentService.findAll().stream().map(Comment::getComment).forEach(System.out::println);
        } else {
            Optional<Comment> optionalComment = commentService.findById(Long.valueOf(id));
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
            Optional<Book> optionalBook = bookService.findById(Long.valueOf(id));
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
            Optional<Genre> optionalGenre = genreService.findById(Long.valueOf(id));
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
            Optional<Author> optionalAuthor = authorService.findById(Long.valueOf(id));
            if (optionalAuthor.isPresent()) {
                System.out.println(optionalAuthor.get());
            } else {
                System.out.println("Автор не найден");
            }
        }
    }

    private void deleteBook(String id) {
        bookService.deleteById(Long.valueOf(id));
        System.out.println("Книга удалена");
    }

    private void deleteGenre(String id) {
        genreService.deleteById(Long.valueOf(id));
        System.out.println("Жанр удален");
    }

    private void deleteAuthor(String id) {
        authorService.deleteById(Long.valueOf(id));
        System.out.println("Автор удален");
    }

    private void deleteComment(String id) {
        commentService.deleteById(Long.valueOf(id));
        System.out.println("Комментарий удален");
    }
}
