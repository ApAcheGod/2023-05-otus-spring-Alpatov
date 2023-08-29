package ru.otus.spring.hw8.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import ru.otus.spring.hw8.annotation.CascadeSave;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {

    private final MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        ReflectionUtils.doWithFields(event.getSource().getClass(), field -> {
            ReflectionUtils.makeAccessible(field);

            if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
                cascadeSave(field.get(event.getSource()));
            }
        });
    }

    private void cascadeSave(Object fieldValue) {
        if (Objects.nonNull(fieldValue)) {
            FieldCallback callback = new FieldCallback();
            if (fieldValue instanceof Collection<?>) {
                ((Collection<?>) fieldValue).forEach(mongoOperations::save);
            } else {
                ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
                mongoOperations.save(fieldValue);
            }
        }
    }

    private static class FieldCallback implements ReflectionUtils.FieldCallback {
        private boolean idFound;

        public void doWith(Field field) {
            ReflectionUtils.makeAccessible(field);

            if (field.isAnnotationPresent(Id.class)) {
                idFound = true;
            }
        }

        public boolean isIdFound() {
            return idFound;
        }
    }

}
