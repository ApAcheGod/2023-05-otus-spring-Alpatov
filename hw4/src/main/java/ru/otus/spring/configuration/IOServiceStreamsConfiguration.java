package ru.otus.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.io.StreamsIOService;

@Configuration
public class IOServiceStreamsConfiguration {

    @Bean
    public StreamsIOService iOServiceStreams() {
        return new StreamsIOService(System.out, System.in);
    }
}