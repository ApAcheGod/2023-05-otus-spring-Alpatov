package ru.otus.spring.hw5.service;

public interface Library {

    void getBook(String id);

    void getGenre(String id);

    void getAuthor(String id);

    void deleteBook(String id);

    void deleteGenre(String id);

    void deleteAuthor(String id);

    void insertGenre(String name);

    void insertAuthor(String name, String lastName);

    void updateBook(String bookId, String bookTitle, String publicationYear, String pagesCount);

    void insertBook(String bookTitle, Integer publicationYear, Integer pagesCount,
                    String authorName, String authorLastName, String genreName);
}
