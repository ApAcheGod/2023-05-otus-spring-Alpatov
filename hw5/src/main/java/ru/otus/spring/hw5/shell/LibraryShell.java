package ru.otus.spring.hw5.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw5.service.Library;

@ShellComponent
@RequiredArgsConstructor
public class LibraryShell {

    private final Library library;

    @ShellMethod(key = {"g", "get"}, value = "get book by id")
    public void getBook(@ShellOption(value = "id", defaultValue = "all") String param) {
        library.getBook(param);
    }

    @ShellMethod(key = {"d", "delete"}, value = "delete book by id")
    public void deleteBook(@ShellOption(value = "id") String param) {
        library.deleteBook(param);
    }

    @ShellMethod(key = {"c", "create"}, value = "create book")
    public void createBook(@ShellOption(value = "b_title", defaultValue = "Unknown") String bookTitle,
                           @ShellOption(value = "b_year", defaultValue = "2023") Integer publicationYear,
                           @ShellOption(value = "b_pages", defaultValue = "77") Integer pagesCount,
                           @ShellOption(value = "a_name", defaultValue = "Unknown") String authorName,
                           @ShellOption(value = "a_ln", defaultValue = "Unknown") String authorLastName,
                           @ShellOption(value = "g_n", defaultValue = "Unknown") String genreName) {
        library.insertBook(bookTitle, publicationYear, pagesCount, authorName, authorLastName, genreName);
    }

    @ShellMethod(key = {"u", "update"}, value = "Update book")
    public void updateBook(@ShellOption(value = "id") String bookId,
                           @ShellOption(value = "t") String title,
                           @ShellOption(value = "y") String year,
                           @ShellOption(value = "p") String pages) {
        library.updateBook(bookId, title, year, pages);
    }
}
