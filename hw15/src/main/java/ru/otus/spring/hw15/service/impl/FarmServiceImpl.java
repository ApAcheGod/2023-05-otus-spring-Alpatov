package ru.otus.spring.hw15.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw15.domain.Breed;
import ru.otus.spring.hw15.domain.Egg;
import ru.otus.spring.hw15.service.FarmService;
import ru.otus.spring.hw15.service.IntegrationGateway;

import java.util.concurrent.ForkJoinPool;

@Service
@Slf4j
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final IntegrationGateway integrationGateway;

    @Override
    public void startProduction() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            forkJoinPool.execute(() -> {
                log.info(finalI + " generate Egg");
                integrationGateway.process(generateEgg());
            });
        }
    }

    private static Egg generateEgg() {
        return new Egg(Breed.values()[RandomUtils.nextInt(0, Breed.values().length)]);
    }
}
