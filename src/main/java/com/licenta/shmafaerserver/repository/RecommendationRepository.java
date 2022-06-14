package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.Recommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    Page<Recommendation> findAllByUserOrderByCreatedAtDesc(AppUser user, Pageable page);

    Long countByUser(AppUser user);

    //@Query("SELECT r2 FROM Recommendation r2 ORDER BY r2.createdAt DESC LIMIT 10")
    //void deleteThat();

    //List<Recommendation> findFirst10ByOrderByCreatedAtDesc();
    //List<Recommendation> findTop10ByUserOrderByCreatedAtDesc(AppUser user);
    List<Recommendation> findTop10ByUserOrderByCreatedAtAsc(AppUser user);
}
