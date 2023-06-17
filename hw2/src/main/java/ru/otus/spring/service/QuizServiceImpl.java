package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Quiz;
import ru.otus.spring.domain.Student;
import ru.otus.spring.repository.QuizRepository;
import ru.otus.spring.repository.StudentRepository;
import ru.otus.spring.service.io.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService{

    private final QuizRepository quizRepository;

    private final StudentRepository studentRepository;

    private final IOService iOServiceStreams;

    private final TemplateService templateService;

    @Override
    public void startQuiz() {
        Student student = studentRepository.getStudent();
        List<Quiz> quizList = quizRepository.getAll();
        int studentResult = getStudentResult(quizList);
        String resultMessage = templateService.getResultMessage(student, studentResult);
        iOServiceStreams.outputString(resultMessage);
    }

    private String askQuestionAndGetAnswer(Quiz quiz) {
        return iOServiceStreams.readStringWithPrompt(quiz.getQuestion());
    }

    private int checkAnswer(Quiz quiz, String userAnswer) {
        return quiz.getAnswer().equals(userAnswer) ? 1 : 0;
    }

    private int getStudentResult(List<Quiz> quizzes) {
        return quizzes
                .stream()
                .mapToInt(quiz -> checkAnswer(quiz, askQuestionAndGetAnswer(quiz)))
                .sum();
    }
}