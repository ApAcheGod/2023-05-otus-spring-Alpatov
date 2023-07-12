package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.configuration.ApplicationProperties;
import ru.otus.spring.domain.Quiz;
import ru.otus.spring.domain.Student;
import ru.otus.spring.repository.CsvQuizRepository;
import ru.otus.spring.repository.QuizRepository;
import ru.otus.spring.repository.StudentRepository;
import ru.otus.spring.service.io.IOService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {
        QuizServiceImpl.class,
        CsvQuizRepository.class
})
class QuizServiceImplTest {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRepository quizRepository;

    @MockBean
    private ResourceProvider resourceProvider;

    @MockBean
    private ApplicationMessage applicationMessage;

    @MockBean
    private LocaleProvider localeProvider;

    @MockBean
    private QuizProperties quizProperties;

    @MockBean
    private TemplateService templateService;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private IOService iOServiceStreams;


    private Student student;
    private List<Quiz> quizList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        student = Student.builder().name("Nikita").surname("Alpatov").build();
        when(studentRepository.getStudent()).thenReturn(student);
        when(applicationMessage.getMessage(Mockito.any())).thenReturn("questions_en.csv");
        quizList = quizRepository.getAll();
        quizList.forEach(quiz -> {
            when(iOServiceStreams.readStringWithPrompt(quiz.getQuestion())).thenReturn(quiz.getAnswer());
        });
        when(templateService.getResultMessage(any(), anyInt())).thenReturn("Grate");
    }

    @Test
    void inputTest() {
        quizService.startQuiz();
        Assertions.assertEquals(5, quizList.size());
        verify(iOServiceStreams, times(1)).outputString(anyString());

    }
}