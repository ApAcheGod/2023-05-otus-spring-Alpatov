package ru.otus.spring.hw8.event;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw8.annotation.Sequence;
import ru.otus.spring.hw8.domain.AbstractDocument;
import ru.otus.spring.hw8.service.SequenceGenerator;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SequenceMongoEventListener extends AbstractMongoEventListener<Object> {

    private final SequenceGenerator sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {

        Sequence annotation = AnnotationUtils.findAnnotation(event.getSource().getClass(), Sequence.class);
        if (annotation != null) {
            AbstractDocument source = (AbstractDocument) event.getSource();
            if (Objects.isNull(source.getId())) {
                source.setId(sequenceGenerator.getNext(annotation.value()));
            }
        }
    }
}
