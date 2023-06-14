package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.io.InputService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final InputService iOServiceStreams;

    @Override
    public Student getStudentInfo() {
        String[] userDate = iOServiceStreams.readString().split(" ");
        return createStudent(userDate);
    }

    private Student createStudent(String[] userDate) {
        return Student.builder()
                .name(userDate[0])
                .surname(userDate[1])
                .build();
    }
}