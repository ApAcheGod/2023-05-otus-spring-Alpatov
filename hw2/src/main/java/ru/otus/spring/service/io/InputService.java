package ru.otus.spring.service.io;

public interface InputService {

    String readString();

    String readStringWithPrompt(String prompt);

}