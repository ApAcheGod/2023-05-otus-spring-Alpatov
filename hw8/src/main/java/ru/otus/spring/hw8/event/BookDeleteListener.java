package ru.otus.spring.hw8.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw8.domain.Book;
import ru.otus.spring.hw8.repository.CommentRepository;

@Component
@RequiredArgsConstructor
public class BookDeleteListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        commentRepository.deleteAllByBookId(event.getSource().getLong("_id"));
        super.onAfterDelete(event);
    }
}
