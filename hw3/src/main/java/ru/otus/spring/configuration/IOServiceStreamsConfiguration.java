package ru.otus.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.io.IOServiceStreams;

@Configuration
public class IOServiceStreamsConfiguration {

    @Bean
    public IOServiceStreams iOServiceStreams() {
        return new IOServiceStreams(System.out, System.in);
    }
}