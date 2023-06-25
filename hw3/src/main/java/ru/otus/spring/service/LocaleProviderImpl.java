package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.configuration.ApplicationProperties;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class LocaleProviderImpl implements LocaleProvider{

    private final ApplicationProperties applicationProperties;

    @Override
    public Locale getLocale() {
        return applicationProperties.getLocale();
    }
}
