package ru.otus.spring.hw14.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class JobShell {

    private final JobLauncher jobLauncher;

    private final Job migrationJob;


    @ShellMethod(key = {"s"}, value = "Start migration")
    public void startMigrationJob() throws Exception {
        jobLauncher.run(migrationJob, new JobParameters());
    }

}
