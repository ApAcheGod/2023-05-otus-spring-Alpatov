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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.spring.hw14.domain.Comment;

import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
@RequiredArgsConstructor
public class CommentMigrationStepConfig {

    private final DataSource dataSource;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    @Bean
    @StepScope
    public MongoItemReader<Comment> commentMongoItemReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Comment>()
                .name("commentMongoItemReader")
                .template(mongoTemplate)
                .targetType(Comment.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Comment> commentJdbcBatchItemWriter() {
        JdbcBatchItemWriter<Comment> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        jdbcBatchItemWriter.setSql("INSERT INTO COMMENT(COMMENT, BOOK_ID) " +
                "VALUES (:comment, (select postgres_id from TEMP_IDS_MAPPING WHERE mongo_id =:book.id))");
        jdbcBatchItemWriter.setDataSource(dataSource);
        return jdbcBatchItemWriter;
    }

    @Bean
    public Step commentStep(MongoItemReader<Comment> commentMongoItemReader,
                            JdbcBatchItemWriter<Comment> commentJdbcBatchItemWriter) {
        return new StepBuilder("commentStep", jobRepository)
                .<Comment, Comment>chunk(10, transactionManager)
                .reader(commentMongoItemReader)
                .writer(commentJdbcBatchItemWriter)
                .allowStartIfComplete(true)
                .build();
    }
}
