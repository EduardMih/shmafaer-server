package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.RecommendationTask;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationTaskRepository extends JpaRepository<RecommendationTask, Long> {
    Slice<RecommendationTask> findTop5ByProcessed(boolean processed);
}
