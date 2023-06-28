package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Student;
import ru.otus.spring.repository.ConsoleStudentRepository;
import ru.otus.spring.service.io.IOService;
import ru.otus.spring.service.io.InputService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsoleStudentRepositoryTest {

    @Mock
    private IOService iOServiceStreams;

    @InjectMocks
    private ConsoleStudentRepository studentServiceImpl;

    @Test
    void getStudentInfo() {
        when(iOServiceStreams.readString()).thenReturn("Nikita Alpatov");

        Student student = studentServiceImpl.getStudent();

        assertEquals("Alpatov", student.getSurname());
    }
}