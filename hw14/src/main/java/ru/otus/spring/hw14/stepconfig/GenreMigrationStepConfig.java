package ru.otus.spring.hw14.stepconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.spring.hw14.domain.Genre;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class GenreMigrationStepConfig {

    private final DataSource dataSource;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    @Bean
    @StepScope
    public MongoItemReader<Genre> genreMongoItemReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Genre>()
                .name("genreMongoItemReader")
                .template(mongoTemplate)
                .targetType(Genre.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Genre> genreInsertTempTable() {
        JdbcBatchItemWriter<Genre> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        jdbcBatchItemWriter.setSql("INSERT INTO TEMP_IDS_MAPPING(mongo_id, postgres_id) " +
                "VALUES (:id, gen_random_uuid())");
        jdbcBatchItemWriter.setDataSource(dataSource);
        return jdbcBatchItemWriter;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Genre> genreJdbcBatchItemWriter() {
        JdbcBatchItemWriter<Genre> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        jdbcBatchItemWriter.setSql("INSERT INTO GENRE(ID, NAME) " +
                "VALUES ((select postgres_id from TEMP_IDS_MAPPING WHERE mongo_id =:id), :name)");
        jdbcBatchItemWriter.setDataSource(dataSource);
        return jdbcBatchItemWriter;
    }

    @Bean
    @StepScope
    public CompositeItemWriter<Genre> genreCompositeItemWriter(JdbcBatchItemWriter<Genre> genreInsertTempTable,
                                                               JdbcBatchItemWriter<Genre> genreJdbcBatchItemWriter) {
        CompositeItemWriter<Genre> writer = new CompositeItemWriter<>();
        writer.setDelegates(Arrays.asList(genreInsertTempTable, genreJdbcBatchItemWriter));
        return writer;
    }

    @Bean
    public Step genreStep(MongoItemReader<Genre> genreMongoItemReader,
                          CompositeItemWriter<Genre> genreCompositeItemWriter) {
        return new StepBuilder("genreStep", jobRepository)
                .<Genre, Genre>chunk(10, transactionManager)
                .reader(genreMongoItemReader)
                .writer(genreCompositeItemWriter)
                .allowStartIfComplete(true)
                .build();
    }
}
