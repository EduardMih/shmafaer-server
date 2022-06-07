package com.licenta.shmafaerserver.converter;

import com.licenta.shmafaerserver.dto.github.ItemSimplifiedDTO;
import com.licenta.shmafaerserver.dto.response.RecommendationDTO;
import com.licenta.shmafaerserver.model.Recommendation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class RecommendationConverter {
    private final ModelMapper modelMapper;
    private final static String SH_BROWSE_BASE = "https://archive.softwareheritage.org/browse/origin/directory/?origin_url=";

    public Recommendation convertItemsToEntity(ItemSimplifiedDTO item)
    {
        Recommendation recommendation = modelMapper.map(item, Recommendation.class);

        recommendation.setHtmlUrl(item.getHtml_url());
        recommendation.setStargazersCount(item.getStargazers_count());

        recommendation.setTopics(null);

        if(!item.getTopics().isEmpty())
        {
            recommendation.setTopics(String.join(", ", item.getTopics()));
        }

        recommendation.setSHBrowseUrl(SH_BROWSE_BASE + item.getHtml_url());
        recommendation.setCreatedAt(new Date());

        return recommendation;

    }

    public RecommendationDTO convertEntityToRecommendationDTO(Recommendation recommendation)
    {
        RecommendationDTO dto = modelMapper.map(recommendation, RecommendationDTO.class);

        dto.setHtmlUrl(recommendation.getHtmlUrl());
        dto.setStargazersCount(recommendation.getStargazersCount());
        if(recommendation.getTopics() != null)
        {
            dto.setTopics(Arrays.asList(recommendation.getTopics().split(", ")));
        }
        dto.setShBrowseUrl(recommendation.getSHBrowseUrl());

        return dto;

    }
}
