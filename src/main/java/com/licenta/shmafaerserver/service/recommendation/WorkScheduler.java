package com.licenta.shmafaerserver.service.recommendation;

import com.licenta.shmafaerserver.model.RecommendationTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkScheduler {
    private final RecommendationTaskService recommendationTaskService;
    private final RecommendationService recommendationService;
    private final WorkerExecutor workerExecutor;

    @Scheduled(fixedDelay = 20000)
    public void addWorkers()
    {
        List<RecommendationTask> tasks;
        Worker worker;
        boolean added;

        log.info(Thread.currentThread().getName() + new Date());

        tasks = recommendationTaskService.readTasks();

        for(RecommendationTask task: tasks)
        {
            worker = new Worker(task, recommendationService);
            added = workerExecutor.addTask(worker);
            if(added)
            {
                recommendationTaskService.markTaskAsProcessed(task);
                log.info("Task " + task.getText() + "added successfully");
            }
        }
    }
}
