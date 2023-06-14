package ru.otus.spring.service;

import ru.otus.spring.domain.Student;

public interface ResultAnalyzeService {

    void outputResult(Student student, int countOfRightAnswers);

}