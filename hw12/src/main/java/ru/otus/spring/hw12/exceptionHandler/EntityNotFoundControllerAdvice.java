package ru.otus.spring.hw12.exceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.spring.hw12.annotation.EntityNotFoundExceptionHandler;

@ControllerAdvice(annotations = EntityNotFoundExceptionHandler.class)
public class EntityNotFoundControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.badRequest().body("Ничего не найдено");
    }
}
