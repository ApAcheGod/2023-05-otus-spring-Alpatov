package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.QuizService;
import ru.otus.spring.service.QuizServiceImpl;

public class StudentTestingApplication {

    public static void main(String[] args) {
        var ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        QuizService quizService = ctx.getBean("quizService", QuizServiceImpl.class);
        quizService.getQuizAndPrint();
    }
}
