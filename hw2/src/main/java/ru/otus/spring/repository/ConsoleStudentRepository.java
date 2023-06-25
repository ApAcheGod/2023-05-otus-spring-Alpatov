package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.io.IOService;

@Service
@RequiredArgsConstructor
public class ConsoleStudentRepository implements StudentRepository {

    private static final String REQUEST_USER_DATA_MESSAGE = "Please, Enter your name and surname";

    private final IOService iOServiceStreams;

    @Override
    public Student getStudent() {
        requestUserInfo();
        String[] userDate = iOServiceStreams.readString().split(" ");
        return createStudent(userDate);
    }

    private Student createStudent(String[] userDate) {
        return Student.builder()
                .name(userDate[0])
                .surname(userDate[1])
                .build();
    }

    private void requestUserInfo() {
        iOServiceStreams.outputString(REQUEST_USER_DATA_MESSAGE);
    }
}