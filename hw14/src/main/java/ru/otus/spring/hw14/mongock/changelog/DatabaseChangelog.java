package ru.otus.spring.hw14.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.hw14.domain.Author;
import ru.otus.spring.hw14.domain.Book;
import ru.otus.spring.hw14.domain.Comment;
import ru.otus.spring.hw14.domain.Genre;
import ru.otus.spring.hw14.repository.AuthorRepository;
import ru.otus.spring.hw14.repository.BookRepository;
import ru.otus.spring.hw14.repository.CommentRepository;
import ru.otus.spring.hw14.repository.GenreRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDB", author = "alpatov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "initDb", author = "alpatov", runAlways = true)
    public void initDb(GenreRepository genreRepository,
                       AuthorRepository authorRepository,
                       BookRepository bookRepository,
                       CommentRepository commentRepository) {
        List<Genre> genres = List.of(new Genre("Роман"),
                new Genre("Драма"), new Genre("Комедия"),
                new Genre("Трагедия"), new Genre("Повесть"),
                new Genre("Рассказ"), new Genre("Поэма"), new Genre("Утопия"));

        List<Author> authors = List.of(new Author("Михаил", "Булгаков"),
                new Author("Федор", "Достоевский"), new Author("Эрих Мария", "Ремарк"),
                new Author("Джером", "Сэлинджер"), new Author("Михаил", "Лермонтов"),
                new Author("Оскар", "Уайльд"));

        List<Book> books = List.of(
            new Book("Мастер и Маргарита",1876,432,
                   List.of(authors.get(0)), List.of(genres.get(0))),
            new Book("Преступление и наказание",1956,321,
                   List.of(authors.get(1)), List.of(genres.get(1))),
            new Book("Три товарища",1853,543,
                    List.of(authors.get(2)), List.of(genres.get(2), genres.get(3))),
            new Book("Над пропастью во ржи",1936,543,
                    List.of(authors.get(3)), List.of(genres.get(4), genres.get(5))),
            new Book("Герой нашего времени",1956,234,
                    List.of(authors.get(4)), List.of(genres.get(5), genres.get(6))),
            new Book("Портрет Дориана Грея",1875,324,
                    List.of(authors.get(5)), List.of(genres.get(5))));

        genreRepository.saveAll(genres);
        authorRepository.saveAll(authors);
        bookRepository.saveAll(books);

        List<Comment> comments = books.stream().map(book -> new Comment("Comment", book)).toList();

        commentRepository.saveAll(comments);
    }
}
