package ru.otus.spring.hw8.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw8.service.LibraryService;

@ShellComponent
@RequiredArgsConstructor
public class LibraryShell {

    private final LibraryService libraryService;

    @ShellMethod(key = {"g", "get"}, value = "get by id")
    public void get(@ShellOption(value = "s",
            defaultValue = "Unknown",
            help = "b - book, a - author, g - genre, c - comment") String service,
                    @ShellOption(value = "id", defaultValue = "all") String param) {
        libraryService.get(service, param);
    }

    @ShellMethod(key = {"d", "delete"}, value = "delete book by id")
    public void delete(@ShellOption(value = "s",
            defaultValue = "Unknown",
            help = "b - book, a - author, g - genre, c - comment") String service,
                       @ShellOption(value = "id") String param) {
        libraryService.delete(service, param);
    }

    @ShellMethod(key = {"c", "create"}, value = "create book")
    public void create(@ShellOption(value = "b_title", defaultValue = "Unknown") String bookTitle,
                       @ShellOption(value = "b_year", defaultValue = "2023") Integer publicationYear,
                       @ShellOption(value = "b_pages", defaultValue = "77") Integer pagesCount,
                       @ShellOption(value = "a_name", defaultValue = "Unknown") String authorName,
                       @ShellOption(value = "a_ln", defaultValue = "Unknown") String authorLastName,
                       @ShellOption(value = "g_n", defaultValue = "Unknown") String genreName) {
        libraryService.insertBook(bookTitle, publicationYear, pagesCount, authorName, authorLastName, genreName);
    }

    @ShellMethod(key = {"u", "update"}, value = "Update book")
    public void update(@ShellOption(value = "id") String bookId,
                       @ShellOption(value = "t") String title,
                       @ShellOption(value = "y") String year,
                       @ShellOption(value = "p") String pages) {
        libraryService.updateBook(bookId, title, year, pages);
    }

    @ShellMethod(key = {"com", "comment"}, value = "comment the book")
    public void insertComment(@ShellOption(value = "t", defaultValue = "Unknown") String bookTitle,
                        @ShellOption(value = "c", defaultValue = "") String commentText) {
        libraryService.insertComment(bookTitle, commentText);
    }

    @ShellMethod(key = {"gc", "getComments"}, value = "get book comments ")
    public void getCommentsByBook(@ShellOption(value = "t", defaultValue = "Unknown") String bookTitle) {
        libraryService.getComments(bookTitle);
    }

    @ShellMethod(key = {"uc", "updateComment"}, value = "update comment")
    public void updateComment(@ShellOption(value = "id") String commentId,
                              @ShellOption(value = "t", defaultValue = "") String commentText) {
        libraryService.updateComment(commentId, commentText);
    }

}

