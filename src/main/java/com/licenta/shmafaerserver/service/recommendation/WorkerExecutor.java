package com.licenta.shmafaerserver.service.recommendation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkerExecutor {
    private final ThreadPoolTaskExecutor executor;

    public boolean addTask(Runnable worker)
    {
        try
        {
            executor.execute(worker);

            return true;

        }
        catch(RejectedExecutionException e)
        {
            log.error(e.getMessage());

            return false;

        }
    }
}
