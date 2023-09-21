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
import ru.otus.spring.hw14.domain.Author;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class AuthorMigrationStepConfig {

    private final DataSource dataSource;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    @Bean
    @StepScope
    public MongoItemReader<Author> authorMongoItemReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Author>()
                .name("authorMongoItemReader")
                .template(mongoTemplate)
                .targetType(Author.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Author> authorInsertTempTable() {
        JdbcBatchItemWriter<Author> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        jdbcBatchItemWriter.setSql("INSERT INTO TEMP_IDS_MAPPING(mongo_id, postgres_id) " +
                "VALUES (:id, gen_random_uuid())");
        jdbcBatchItemWriter.setDataSource(dataSource);
        return jdbcBatchItemWriter;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Author> authorJdbcBatchItemWriter() {
        JdbcBatchItemWriter<Author> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        jdbcBatchItemWriter.setSql("INSERT INTO AUTHOR(ID, NAME, LAST_NAME) " +
                "VALUES ((select postgres_id from TEMP_IDS_MAPPING WHERE mongo_id =:id), :name, :lastName)");
        jdbcBatchItemWriter.setDataSource(dataSource);
        return jdbcBatchItemWriter;
    }

    @Bean
    @StepScope
    public CompositeItemWriter<Author> authorCompositeItemWriter(JdbcBatchItemWriter<Author> authorInsertTempTable,
                                                                JdbcBatchItemWriter<Author> authorJdbcBatchItemWriter) {
        CompositeItemWriter<Author> writer = new CompositeItemWriter<>();
        writer.setDelegates(Arrays.asList(authorInsertTempTable, authorJdbcBatchItemWriter));
        return writer;
    }


    @Bean
    public Step authorStep(MongoItemReader<Author> authorMongoItemReader,
                           CompositeItemWriter<Author> authorCompositeItemWriter) {
        return new StepBuilder("authorStep", jobRepository)
                .<Author, Author>chunk(10, transactionManager)
                .reader(authorMongoItemReader)
                .writer(authorCompositeItemWriter)
                .allowStartIfComplete(true)
                .build();
    }
}
