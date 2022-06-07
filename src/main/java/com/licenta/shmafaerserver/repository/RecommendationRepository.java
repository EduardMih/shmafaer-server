package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.Recommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    Page<Recommendation> findAllByUserOrderByCreatedAtDesc(AppUser user, Pageable page);
}
