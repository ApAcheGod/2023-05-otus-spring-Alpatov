package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.configuration.ApplicationProperties;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.ApplicationMessage;
import ru.otus.spring.service.io.IOService;


@Service
@RequiredArgsConstructor
public class ConsoleStudentRepository implements StudentRepository {

    private final IOService iOServiceStreams;

    private final ApplicationProperties applicationProperties;

    private final ApplicationMessage applicationMessage;

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
        iOServiceStreams.outputString(getMessage());
    }

    private String getMessage() {
        return applicationMessage.getMessage(applicationProperties.getRequestUserData());
    }
}