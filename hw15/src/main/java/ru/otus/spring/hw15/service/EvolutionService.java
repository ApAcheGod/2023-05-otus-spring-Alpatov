package ru.otus.spring.hw15.service;

import ru.otus.spring.hw15.domain.Chick;
import ru.otus.spring.hw15.domain.Egg;
import ru.otus.spring.hw15.domain.HatchedChicken;
import ru.otus.spring.hw15.domain.Hen;

import java.util.List;

public interface EvolutionService {

    HatchedChicken evolute(Egg egg);

    Chick evolute(HatchedChicken hatchedChicken);

    Hen evolute(Chick chick);

    List<Egg> layEggs(Hen hen);
}
