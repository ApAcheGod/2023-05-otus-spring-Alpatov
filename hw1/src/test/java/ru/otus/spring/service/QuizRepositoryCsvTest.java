package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/spring-test-context.xml")
class QuizRepositoryCsvTest {

    private final QuizRepository quizRepositoryCsv;

    @Autowired
    public QuizRepositoryCsvTest(QuizRepository quizRepositoryCsv) {
        this.quizRepositoryCsv = quizRepositoryCsv;
    }

    @Test
    @DisplayName("Проверка количества элементов")
    void readFile() {
        Assertions.assertEquals(5, quizRepositoryCsv.getAll().size());
    }
}