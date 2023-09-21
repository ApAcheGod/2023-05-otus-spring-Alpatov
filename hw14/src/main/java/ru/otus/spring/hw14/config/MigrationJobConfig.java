package ru.otus.spring.hw14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class MigrationJobConfig {

    private final DataSource dataSource;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job migrationJob(Step genreStep, Step authorStep, Step bookStep, Step commentStep) {
        return new JobBuilder("migrationJob", jobRepository)
                .start(createIdsLinkTable(jobRepository))
                .next(genreStep)
                .next(authorStep)
                .next(bookStep)
                .next(commentStep)
                .next(dropIdsLinkTable(jobRepository))
                .build();
    }

    @Bean
    public TaskletStep createIdsLinkTable(JobRepository jobRepository) {
        return new StepBuilder("createIdsLinkTable", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(((contribution, chunkContext) -> {
                    new JdbcTemplate(dataSource).execute(
                           "CREATE TABLE TEMP_IDS_MAPPING (mongo_id char(24) not null, postgres_id uuid not null)");
                    return RepeatStatus.FINISHED;
                }), transactionManager)
                .build();
    }

    @Bean
    TaskletStep dropIdsLinkTable(JobRepository jobRepository) {
        return new StepBuilder("dropIdsLinkTable", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(((contribution, chunkContext) -> {
                    new JdbcTemplate(dataSource).execute("DROP TABLE TEMP_IDS_MAPPING");
                    return RepeatStatus.FINISHED;
                }), transactionManager)
                .build();
    }
}
