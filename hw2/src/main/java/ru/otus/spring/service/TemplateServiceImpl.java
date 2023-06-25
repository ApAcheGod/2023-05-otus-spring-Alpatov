package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

@Service
public class TemplateServiceImpl implements TemplateService {

    private final int minimumPassingScore;

    public TemplateServiceImpl(@Value("${minimum.passing.score}") int minimumPassingScore) {
        this.minimumPassingScore = minimumPassingScore;
    }

    @Override
    public String getResultMessage(Student student, int countOfRightAnswers) {
        if (countOfRightAnswers >= minimumPassingScore) {
            return String.format("Dear, %s. You have scored the required number of points. Your result is %d",
                    student.getName(),
                    countOfRightAnswers);
        } else {
            return String.format("Dear, %s. You haven't scored the required number of points. Your result is %d",
                    student.getName(),
                    countOfRightAnswers);
        }
    }
}
