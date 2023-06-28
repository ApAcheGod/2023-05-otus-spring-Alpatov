package ru.otus.spring.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.service.ApplicationMessage;
import ru.otus.spring.service.ResourceProvider;

@SpringBootTest(classes = QuizRepositoryCsv.class)
class QuizRepositoryCsvTest {

    @Autowired
    private QuizRepository quizRepository;

    @MockBean
    private ResourceProvider applicationProperties;

    @MockBean
    private ApplicationMessage applicationMessage;


    @Test
    void getAll() {
        Mockito.when(applicationMessage.getMessage(Mockito.any())).thenReturn("questions_en.csv");
        Assertions.assertEquals(5, quizRepository.getAll().size());
    }
}