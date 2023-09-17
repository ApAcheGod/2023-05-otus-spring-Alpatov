package ru.otus.spring.hw11.mongock.changelog;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import io.mongock.driver.mongodb.reactive.util.MongoSubscriberSync;
import io.mongock.driver.mongodb.reactive.util.SubscriberSync;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw11.domain.Author;
import ru.otus.spring.hw11.domain.Book;
import ru.otus.spring.hw11.domain.Comment;
import ru.otus.spring.hw11.domain.Genre;
import ru.otus.spring.hw11.repository.AuthorRepository;
import ru.otus.spring.hw11.repository.BookRepository;
import ru.otus.spring.hw11.repository.CommentRepository;
import ru.otus.spring.hw11.repository.GenreRepository;

import java.util.List;

@ChangeUnit(order = "001", id = "initDb2", author = "alpatov", runAlways = true)
@RequiredArgsConstructor
public class DatabaseChangelog {

    private final MongoDatabase mongoDatabase;

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

        @Execution
        public void initDb() throws InterruptedException {
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

            List<Comment> comments = books.stream().map(book -> new Comment("Comment", book)).toList();

            genreRepository.deleteAll()
                    .thenMany(authorRepository.deleteAll())
                    .thenMany(bookRepository.deleteAll())
                    .thenMany(commentRepository.deleteAll())
                    .thenMany(genreRepository.saveAll(genres))
                    .thenMany(authorRepository.saveAll(authors))
                    .thenMany(bookRepository.saveAll(books))
                    .thenMany(commentRepository.saveAll(comments))
                    .blockLast();
        }

    @RollbackExecution
    public void rollbackExecution() {
        SubscriberSync<DeleteResult> subscriber = new MongoSubscriberSync<>();
        Flux.from(mongoDatabase.listCollectionNames())
                .flatMap(collectionName ->
                        Mono.from(mongoDatabase.getCollection(collectionName)
                                .deleteMany(new Document()))
                ).subscribe(subscriber);
        subscriber.await();
    }
}
