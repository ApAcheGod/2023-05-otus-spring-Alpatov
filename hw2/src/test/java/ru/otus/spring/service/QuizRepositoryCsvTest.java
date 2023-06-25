package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.repository.QuizRepository;
import ru.otus.spring.repository.QuizRepositoryCsv;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = QuizRepositoryCsv.class)
@TestPropertySource(properties = "questions.path=testQuestions.csv")
class QuizRepositoryCsvTest {

    @Autowired
    public QuizRepository quizRepositoryCsv;

    @Test
    @DisplayName("Проверка количества элементов")
    void readFile() {
        Assertions.assertEquals(5, quizRepositoryCsv.getAll().size());
    }
}