package ru.otus.spring.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import ru.otus.spring.service.LocaleProvider;
import ru.otus.spring.service.ResourceProvider;

import java.util.Locale;

@Getter
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties implements LocaleProvider, ResourceProvider {

    private final Locale locale;

    private final String requestUserData;

    private final String path;

    private final String passedMessage;

    private final String failedMessage;

    private final int passingScore;

    @ConstructorBinding
    public ApplicationProperties(Locale locale,
                                 String requestUserData,
                                 String path,
                                 String passedMessage,
                                 String failedMessage, int passingScore) {
        this.locale = locale;
        this.requestUserData = requestUserData;
        this.path = path;
        this.passedMessage = passedMessage;
        this.failedMessage = failedMessage;
        this.passingScore = passingScore;
    }
}

