package ru.otus.spring.hw15.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.hw15.domain.Breed;
import ru.otus.spring.hw15.domain.Hen;
import ru.otus.spring.hw15.service.EvolutionService;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> eggChanel() {
        return MessageChannels.queue(100);
    }

    @Bean
    public MessageChannelSpec<?, ?> henChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow eggFlow(EvolutionService evolutionService) {
        return IntegrationFlow.from(eggChanel())
                .handle(evolutionService, "evolute")
                .handle(evolutionService, "evolute")
                .handle(evolutionService, "evolute")
                .channel(henChannel())
                .get();
    }

    @Bean
    public IntegrationFlow henFlow(EvolutionService evolutionService) {
        return IntegrationFlow.from(henChannel())
                .<Hen>filter(hen -> Breed.EGG_BREED.equals(hen.getBreed()))
                .transform(evolutionService, "layEggs")
                .log(LoggingHandler.Level.WARN,"new eggs")
                .split()
                .channel(eggChanel())
                .get();
    }
}
