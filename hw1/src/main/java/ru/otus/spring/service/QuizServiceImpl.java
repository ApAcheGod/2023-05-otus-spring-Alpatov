package ru.otus.spring.service;

public class QuizServiceImpl implements QuizService{

    private final QuizRepository quizRepository;

    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public void getQuestionsAndPrint() {
        quizRepository.getAll().forEach(quiz -> System.out.println(quiz.getQuestion()));
    }

    @Override
    public void getQuizAndPrint() {
        quizRepository.getAll().forEach(quiz -> System.out.printf("%s - %s%n", quiz.getQuestion(), quiz.getAnswer()));
    }
}
