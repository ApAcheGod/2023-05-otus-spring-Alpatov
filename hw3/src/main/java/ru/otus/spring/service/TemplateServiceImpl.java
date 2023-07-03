package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final ApplicationMessage applicationMessage;

    private final QuizProperties quizProperties;

    @Override
    public String getResultMessage(Student student, int countOfRightAnswers) {
        if (countOfRightAnswers >= quizProperties.getPassingScore()) {
            return String.format(applicationMessage.getMessage(quizProperties.getPassedMessage()),
                    student.getName(),
                    countOfRightAnswers);
        } else {
            return String.format(applicationMessage.getMessage(quizProperties.getFailedMessage()),
                    student.getName(),
                    countOfRightAnswers);
        }
    }
}
