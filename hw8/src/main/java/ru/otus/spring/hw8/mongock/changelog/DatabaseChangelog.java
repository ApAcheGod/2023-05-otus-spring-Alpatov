package ru.otus.spring.hw8.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.hw8.domain.Author;
import ru.otus.spring.hw8.domain.Book;
import ru.otus.spring.hw8.domain.Comment;
import ru.otus.spring.hw8.domain.Genre;
import ru.otus.spring.hw8.repository.BookRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDB", author = "alpatov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "alpatov", runAlways = true)
    public void insertBook(BookRepository bookRepository) {
        List<Book> books = List.of(
                new Book("Мастер и Маргарита",1876,432,
                       List.of(new Author("Михаил","Булгаков")),
                       List.of(new Genre("Роман")), List.of()),
                new Book("Преступление и наказание",1956,321,
                       List.of(new Author("Федор","Достоевский")),
                       List.of(new Genre("Драма")), List.of()),
                new Book("Три товарища",1853,543,
                       List.of(new Author("Эрих Мария","Ремарк")),
                       List.of(new Genre("Комедия"), new Genre("Трагедия")), List.of()),
                new Book("Над пропастью во ржи",1936,543,
                       List.of(new Author("Джером","Сэлинджер")),
                       List.of(new Genre("Повесть"), new Genre("Рассказ")), List.of()),
                new Book("Герой нашего времени",1956,234,
                       List.of(new Author("Михаил","Лермонтов")),
                       List.of(new Genre("Поэма"), new Genre("Рассказ")), List.of()),
                new Book("Портрет Дориана Грея",1875,324,
                       List.of(new Author("Оскар","Уайльд")),
                       List.of(new Genre("Утопия")), List.of()));
        bookRepository.saveAll(books);
    }

    @ChangeSet(order = "005", id = "insertComments", author = "alpatov", runAlways = true)
    public void insertComments(BookRepository bookRepository) {
        for (Book book : bookRepository.findAll()) {
            for (int i = 0; i < 5; i++) {
                Comment comment = new Comment("Comment_" + i, book);
                book.getComments().add(comment);
            }
            bookRepository.save(book);
        }
    }
}
