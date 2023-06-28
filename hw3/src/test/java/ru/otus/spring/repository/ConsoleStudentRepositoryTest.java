package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.ApplicationMessage;
import ru.otus.spring.service.ResourceProvider;
import ru.otus.spring.service.io.IOService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ConsoleStudentRepository.class)
class ConsoleStudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @MockBean
    private IOService iOServiceStreams;

    @MockBean
    private ResourceProvider applicationProperties;

    @MockBean
    private ApplicationMessage applicationMessage;

    @Test
    void getStudent() {
        when(iOServiceStreams.readString()).thenReturn("Nikita Alpatov");

        Student student = studentRepository.getStudent();

        assertEquals("Alpatov", student.getSurname());
    }
}