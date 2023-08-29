package ru.otus.spring.hw7.utils;

import ru.otus.spring.hw7.entity.Author;
import ru.otus.spring.hw7.entity.Book;
import ru.otus.spring.hw7.entity.Comment;
import ru.otus.spring.hw7.entity.Genre;

import java.util.stream.Collectors;

public class PrinterUtils {

    private PrinterUtils() {

    }

    public static void printBook(Book book) {
        System.out.printf("Книга: %s\n", book.getId());
        System.out.printf("Название: %s\n", book.getTitle());
        System.out.printf("Год публикации: %s\n", book.getPublicationYear());
        System.out.printf("Количество страниц: %s\n", book.getPageCount());
        if (book.getGenres().size() == 1) {
            System.out.printf("Жанр: %s\n", book.getGenres().stream().map(Genre::getName).findFirst().get());
        } else if (book.getGenres().size() > 1) {
            System.out.printf("Жанры: %s\n", book.getGenres().stream()
                    .map(Genre::getName)
                    .collect(Collectors.joining(", ")));
        }

        if (book.getAuthors().size() == 1) {
            Author author = book.getAuthors().stream().findFirst().get();
            System.out.printf("Автор: %s %s\n\n", author.getName(), author.getLastName());
        } else if (book.getAuthors().size() > 1) {
            System.out.printf("Авторы: %s\n\n", book.getAuthors()
                    .stream()
                    .map(author -> author.getName() + " " + author.getLastName())
                    .collect(Collectors.joining(", ")));
        }
    }

    public static void printBookWithComments(Book book) {
        System.out.printf("Книга: %s\n", book.getId());
        System.out.printf("Название: %s\n", book.getTitle());
        System.out.printf("Год публикации: %s\n", book.getPublicationYear());
        System.out.printf("Количество страниц: %s\n", book.getPageCount());
        if (book.getGenres().size() == 1) {
            System.out.printf("Жанр: %s\n", book.getGenres().stream().map(Genre::getName).findFirst().get());
        } else if (book.getGenres().size() > 1) {
            System.out.printf("Жанры: %s\n", book.getGenres().stream()
                    .map(Genre::getName)
                    .collect(Collectors.joining(", ")));
        }

        if (book.getAuthors().size() == 1) {
            Author author = book.getAuthors().stream().findFirst().get();
            System.out.printf("Автор: %s %s\n\n", author.getName(), author.getLastName());
        } else if (book.getAuthors().size() > 1) {
            System.out.printf("Авторы: %s\n\n", book.getAuthors()
                    .stream()
                    .map(author -> author.getName() + " " + author.getLastName())
                    .collect(Collectors.joining(", ")));
        }

        book.getComments().stream().map(Comment::getComment).forEach(System.out::println);
    }
}
