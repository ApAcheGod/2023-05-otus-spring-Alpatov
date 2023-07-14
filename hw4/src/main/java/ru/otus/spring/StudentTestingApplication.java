package ru.otus.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.configuration.ApplicationProperties;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties(ApplicationProperties.class)
public class StudentTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentTestingApplication.class, args);
    }

}
