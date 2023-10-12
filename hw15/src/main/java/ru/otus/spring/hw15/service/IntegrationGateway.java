package ru.otus.spring.hw15.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.hw15.domain.Egg;

@MessagingGateway
public interface IntegrationGateway {

    @Gateway(requestChannel = "eggChanel", replyChannel = "henChannel")
    void process(Egg egg);
}
