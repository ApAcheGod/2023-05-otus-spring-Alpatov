package ru.otus.spring.hw8.service;

public interface LibraryService {

    void get(String service, String id);

    void delete(String service, String id);

    void insertGenre(String name);

    void insertAuthor(String name, String lastName);

    void updateBook(String bookId, String bookTitle, String publicationYear, String pagesCount);

    void insertBook(String bookTitle, Integer publicationYear, Integer pagesCount,
                    String authorName, String authorLastName, String genreName);

    void insertComment(String bookTitle, String commentText);

    void getComments(String bookTitle);

    void updateComment(String id, String commentText);

}
