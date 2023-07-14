package ru.otus.spring.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.service.ApplicationMessage;
import ru.otus.spring.service.ResourceProvider;

@SpringBootTest(classes = CsvQuizRepository.class)
class CsvQuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;

    @MockBean
    private ResourceProvider resourceProvider;

    @MockBean
    private ApplicationMessage applicationMessage;


    @Test
    void getAll() {
        Mockito.when(resourceProvider.getPath()).thenReturn("questions_en.csv");
        Assertions.assertEquals(5, quizRepository.getAll().size());
    }
}