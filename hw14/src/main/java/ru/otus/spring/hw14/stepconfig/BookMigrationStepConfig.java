package ru.otus.spring.hw14.stepconfig;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.spring.hw14.domain.Book;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class BookMigrationStepConfig {

    private final DataSource dataSource;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    @Bean
    @StepScope
    public MongoItemReader<Book> bookMongoItemReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Book>()
                .name("bookMongoItemReader")
                .template(mongoTemplate)
                .targetType(Book.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Book> bookInsertTempTable() {
        JdbcBatchItemWriter<Book> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        jdbcBatchItemWriter.setSql("INSERT INTO TEMP_IDS_MAPPING(mongo_id, postgres_id) " +
                "VALUES (:id, gen_random_uuid())");
        jdbcBatchItemWriter.setDataSource(dataSource);
        return jdbcBatchItemWriter;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Book> bookJdbcBatchItemWriter() {
        JdbcBatchItemWriter<Book> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        jdbcBatchItemWriter.setSql("INSERT INTO BOOK(ID, TITLE, PUBLICATION_YEAR, PAGE_COUNT) " +
                "VALUES ((select postgres_id from TEMP_IDS_MAPPING WHERE mongo_id =:id), " +
                ":title, :publicationYear, :pageCount)");
        jdbcBatchItemWriter.setDataSource(dataSource);
        return jdbcBatchItemWriter;
    }

    @Bean
    @StepScope
    public CompositeItemWriter<Book> bookCompositeItemWriter(JdbcBatchItemWriter<Book> bookInsertTempTable,
                                                             JdbcBatchItemWriter<Book> bookJdbcBatchItemWriter,
                                                             ItemWriter<Book> author2BookWriter,
                                                             ItemWriter<Book> book2GenreWriter) {
        CompositeItemWriter<Book> writer = new CompositeItemWriter<>();
        writer.setDelegates(
                Arrays.asList(bookInsertTempTable, bookJdbcBatchItemWriter, author2BookWriter, book2GenreWriter));
        return writer;
    }


    @Bean
    public Step bookStep(MongoItemReader<Book> bookMongoItemReader, CompositeItemWriter<Book> bookCompositeItemWriter) {
        return new StepBuilder("bookStep", jobRepository)
                .<Book, Book>chunk(10, transactionManager)
                .reader(bookMongoItemReader)
                .writer(bookCompositeItemWriter)
                .allowStartIfComplete(true)
                .build();
    }
}
