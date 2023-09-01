package ru.otus.spring.hw8.service;

public interface SequenceGenerator {

    Long getNext(String sequenceName);

}
