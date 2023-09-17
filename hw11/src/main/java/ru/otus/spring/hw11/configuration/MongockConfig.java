package ru.otus.spring.hw11.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import io.mongock.driver.mongodb.reactive.driver.MongoReactiveDriver;
import io.mongock.runner.springboot.MongockSpringboot;
import io.mongock.runner.springboot.base.MongockInitializingBeanRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.hw11.mongock.changelog.DatabaseChangelog;

@Configuration
public class MongockConfig {

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    public MongoReactiveDriver mongoReactiveDriver(MongoClient reactiveMongoClient) {
        return MongoReactiveDriver.withDefaultLock(reactiveMongoClient, database);
    }

    @Bean
    public MongockInitializingBeanRunner mongockInitializingBeanRunner(MongoReactiveDriver driver,
                                                                       ApplicationContext context) {
        return MongockSpringboot.builder()
                .setDriver(driver)
                .addMigrationClass(DatabaseChangelog.class)
                .setSpringContext(context)
                .setTransactionEnabled(false)
                .buildInitializingBeanRunner();
    }
}
