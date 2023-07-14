package ru.otus.spring.service;

import ru.otus.spring.domain.Student;

public interface TemplateService {

    String getResultMessage(Student student, int countOfRightAnswers);

}
