package com.zip.backend.domain.batch;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class BatchScheduler {

  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private HousingDataBatchProcessing housingDataBatchProcessing;

  // 매일 AM 2:00 에 API 에 Update 된 정보 가져오
  @Scheduled(cron = "0 0 2 * * *")
  public void runJob()
      throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
    // job parameter 설정
    Map<String, JobParameter<?>> confMap = new HashMap<>();
    confMap.put("time", new JobParameter(System.currentTimeMillis(), Long.class));
    JobParameters jobParameters = new JobParameters(confMap);

    jobLauncher.run(housingDataBatchProcessing.fetchHousingData(), jobParameters);
  }
}
