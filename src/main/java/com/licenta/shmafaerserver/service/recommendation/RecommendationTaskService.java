package com.licenta.shmafaerserver.service.recommendation;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.RecommendationTask;
import com.licenta.shmafaerserver.repository.RecommendationTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationTaskService {
    private final RecommendationTaskRepository recommendationTaskRepository;

    public void createTask(AppUser user, String text)
    {
        RecommendationTask recommendationTask = new RecommendationTask();

        recommendationTask.setUser(user);
        recommendationTask.setText(text);

        recommendationTaskRepository.save(recommendationTask);

    }

    public List<RecommendationTask> readTasks()
    {

        return recommendationTaskRepository.findTop5ByProcessed(false).getContent();

    }

    public void markTaskAsProcessed(RecommendationTask task)
    {
        task.setProcessed(true);
        recommendationTaskRepository.save(task);
    }
}
