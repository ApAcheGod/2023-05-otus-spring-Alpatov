package ru.otus.spring.hw8.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw8.domain.DatabaseSequence;
import ru.otus.spring.hw8.service.SequenceGenerator;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LibrarySequenceGenerator implements SequenceGenerator {

    private final MongoOperations mongoOperations;

    @Override
    public Long getNext(String sequenceName) {
        DatabaseSequence counter = mongoOperations.findAndModify(
                new Query(Criteria.where("id").is(sequenceName)),
                new Update().inc("seq", 1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return Objects.nonNull(counter)
                ? counter.getSeq()
                : 1;
    }
}
