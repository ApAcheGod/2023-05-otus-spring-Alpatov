package ru.otus.spring.service;

import ru.otus.spring.domain.Quiz;

import java.util.List;

public interface QuizRepository {

    List<Quiz> getAll();
}
