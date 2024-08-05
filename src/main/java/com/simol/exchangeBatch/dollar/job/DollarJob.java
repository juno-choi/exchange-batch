package com.simol.exchangeBatch.dollar.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class DollarJob {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Job dollarJobs() {
        return new JobBuilder("DollarJobStart", jobRepository)
                .start(dollarStep())
                .build();
    }

    @Bean
    @JobScope
    public Step dollarStep() {
        return new StepBuilder("dollarStep", jobRepository)
                .tasklet(tasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    @StepScope
    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("dollar job start");
            return null;
        };
    }
}
