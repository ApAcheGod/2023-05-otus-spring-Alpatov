package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.io.OutputService;

@Service
public class ResultAnalyzerServiceImpl implements ResultAnalyzeService{

    private final OutputService iOServiceStreams;

    private final int minimumPassingScore;

    public ResultAnalyzerServiceImpl(OutputService iOServiceStreams,
                                     @Value("${minimum.passing.score}") int minimumPassingScore) {
        this.iOServiceStreams = iOServiceStreams;
        this.minimumPassingScore = minimumPassingScore;
    }

    @Override
    public void outputResult(Student student, int countOfRightAnswers) {
        if (countOfRightAnswers >= minimumPassingScore) {
            iOServiceStreams.outputString(
                    String.format("Dear, %s. You have scored the required number of points. Your result is %d",
                    student.getName(),
                    countOfRightAnswers));
        } else {
            iOServiceStreams.outputString(
                    String.format("Dear, %s. You haven't scored the required number of points. Your result is %d",
                            student.getName(),
                            countOfRightAnswers));
        }
    }
}
