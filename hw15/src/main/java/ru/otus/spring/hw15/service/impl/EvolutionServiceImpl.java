package ru.otus.spring.hw15.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw15.domain.Chick;
import ru.otus.spring.hw15.domain.Egg;
import ru.otus.spring.hw15.domain.HatchedChicken;
import ru.otus.spring.hw15.domain.Hen;
import ru.otus.spring.hw15.service.EvolutionService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class EvolutionServiceImpl implements EvolutionService {

    @Override
    public HatchedChicken evolute(Egg egg) {
        if (Math.random() < 0.55) {
            log.warn("The egg hatched {}", egg);
            return new HatchedChicken(egg.getBreed());
        }
        log.warn("Egg broke");
        return null;
    }

    @Override
    public Chick evolute(HatchedChicken hatchedChicken) {
        if (Math.random() < 0.65) {
            log.warn("The hatched chicken grown {}", hatchedChicken);
            return new Chick(hatchedChicken.getBreed());
        }
        log.warn("HatchedChicken dead");
        return null;
    }

    @Override
    public Hen evolute(Chick chick) {
        if (Math.random() < 0.75) {
            log.warn("The chicken grown {}", chick);
            return new Hen(chick.getBreed());
        }
        log.warn("HatchedChicken dead");
        return null;
    }

    @Override
    public List<Egg> layEggs(Hen hen) {
        return Stream
                .generate(() -> new Egg(hen.getBreed()))
                .limit(2)
                .collect(Collectors.toList());
    }
}
