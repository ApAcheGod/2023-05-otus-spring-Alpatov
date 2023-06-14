package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.QuizService;

@ComponentScan
@Configuration
@PropertySource(value = {"classpath:application.properties"})
public class StudentTestingApplication {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(StudentTestingApplication.class);
        var quizService = context.getBean("quizServiceImpl", QuizService.class);
        quizService.startQuiz();
    }
}