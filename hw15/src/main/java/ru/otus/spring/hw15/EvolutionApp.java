package ru.otus.spring.hw15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.hw15.service.FarmService;

@SpringBootApplication
public class EvolutionApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(EvolutionApp.class, args);
        var farmService = configurableApplicationContext.getBean(FarmService.class);
        farmService.startProduction();
    }
}
