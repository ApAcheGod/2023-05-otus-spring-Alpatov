package ru.otus.spring.service;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocaleApplicationMessage implements ApplicationMessage {

    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;

    @Override
    public String getMessage(String property) {
        return messageSource.getMessage(property, null, localeProvider.getLocale());
    }
}
