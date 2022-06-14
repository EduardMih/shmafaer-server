package com.licenta.shmafaerserver.model.entitylistener;

import com.licenta.shmafaerserver.model.Recommendation;
import com.licenta.shmafaerserver.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;

@RequiredArgsConstructor
public class RecommendationsListener {
    private final RecommendationRepository recommendationRepository;

    @PostPersist
    private void afterInsertRecommendations(Recommendation recommendation)
    {
       //Long count = recommendationRepository.countByUser()
        System.out.println("Post persist" + recommendation.getHtmlUrl());
    }
}
