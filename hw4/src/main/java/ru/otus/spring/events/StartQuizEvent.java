package ru.otus.spring.events;

import org.springframework.context.ApplicationEvent;

public class StartQuizEvent extends ApplicationEvent  {

    public StartQuizEvent(Object source) {
        super(source);
    }
}
