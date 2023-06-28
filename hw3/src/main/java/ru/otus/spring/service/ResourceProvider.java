package ru.otus.spring.service;

public interface ResourceProvider {

    String getRequestUserData();

    String getPath();

    String getPassedMessage();

    String getFailedMessage();

    int getPassingScore();
}
