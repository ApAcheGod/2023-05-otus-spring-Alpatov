package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final ApplicationMessage applicationMessage;

    private final ResourceProvider applicationProperties;

    @Override
    public String getResultMessage(Student student, int countOfRightAnswers) {
        if (countOfRightAnswers >= applicationProperties.getPassingScore()) {
            return String.format(applicationMessage.getMessage(applicationProperties.getPassedMessage()),
                    student.getName(),
                    countOfRightAnswers);
        } else {
            return String.format(applicationMessage.getMessage(applicationProperties.getFailedMessage()),
                    student.getName(),
                    countOfRightAnswers);
        }
    }
}
