package com.licenta.shmafaerserver.service.recommendation;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.RecommendationTask;
import com.licenta.shmafaerserver.repository.RecommendationTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        recommendationTask.setCreatedAt(LocalDateTime.now());

        if(!checkAlreadyExists(user, text))
        {
            recommendationTaskRepository.save(recommendationTask);
            //System.out.println("Recom" + recommendationTask.getText());
        }

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

    private boolean checkAlreadyExists(AppUser user, String text)
    {
        List<RecommendationTask> temp = recommendationTaskRepository.check(user, text, LocalDateTime.now().minusHours(2));

        return temp != null && !temp.isEmpty();

    }
}
