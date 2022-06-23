package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.RecommendationTask;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecommendationTaskRepository extends JpaRepository<RecommendationTask, Long> {
    Slice<RecommendationTask> findTop5ByProcessed(boolean processed);

    @Query("SELECT r FROM RecommendationTask r WHERE r.user=:user AND r.text =:text AND r.createdAt > :createdAt")
    List<RecommendationTask> check(@Param("user") AppUser user,
                                   @Param("text") String text,
                                   @Param("createdAt") LocalDateTime createdAt);
}
