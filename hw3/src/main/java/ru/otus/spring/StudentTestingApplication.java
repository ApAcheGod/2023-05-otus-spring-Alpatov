package ru.otus.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.configuration.ApplicationProperties;
import ru.otus.spring.service.QuizService;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties(ApplicationProperties.class)
public class StudentTestingApplication implements CommandLineRunner {

    private final QuizService quizService;

    public static void main(String[] args) {
        SpringApplication.run(StudentTestingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        quizService.startQuiz();
    }
}
