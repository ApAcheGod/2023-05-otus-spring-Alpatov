package ru.otus.spring.repository;

import ru.otus.spring.domain.Quiz;

import java.util.List;

public interface QuizRepository {

    List<Quiz> getAll();

}