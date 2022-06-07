package com.licenta.shmafaerserver.service.recommendation;

import com.licenta.shmafaerserver.model.RecommendationTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;


@RequiredArgsConstructor
@Slf4j
public class Worker implements Runnable{
    private final RecommendationTask task;
    private final RecommendationService recommendationService;

    @Override
    public void run()
    {
        log.info(Thread.currentThread().getName() + new Date() + "->" + task.getText());
        recommendationService.createRecommendations(task.getUser(), task.getText());
        log.info(Thread.currentThread().getName() + new Date() + " Finished");
    }
}
