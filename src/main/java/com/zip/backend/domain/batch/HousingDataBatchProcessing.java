package com.zip.backend.domain.batch;

import com.zip.backend.domain.batch.task.FirstTaskLet;
import com.zip.backend.domain.batch.task.SecondTaskLet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class HousingDataBatchProcessing {

  @Autowired
  JobRepository jobRepository;

  @Autowired
  PlatformTransactionManager platformTransactionManager;

  private final FirstTaskLet firstTaskLet;
  private final SecondTaskLet secondTaskLet;


  public HousingDataBatchProcessing(FirstTaskLet firstTaskLet, SecondTaskLet secondTaskLet) {
    this.firstTaskLet = firstTaskLet;
    this.secondTaskLet = secondTaskLet;
  }

  @Bean
  public Job fetchHousingData() {
    return new JobBuilder("HousingJob", jobRepository)
        .start(step1())
        .next(step2())
        .build();
  }

  @Bean
  public Step step1() {
    return new StepBuilder("GetHousingData", jobRepository)
        .tasklet(firstTaskLet, platformTransactionManager)
        .build();
  }

  @Bean
  public Step step2() {
    return new StepBuilder("GetHousingImageData", jobRepository)
        .tasklet(secondTaskLet, platformTransactionManager)
        .build();
  }

}
