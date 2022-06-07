package com.licenta.shmafaerserver.service.recommendation;

import com.licenta.shmafaerserver.converter.RecommendationConverter;
import com.licenta.shmafaerserver.dto.github.ItemSimplifiedDTO;
import com.licenta.shmafaerserver.dto.github.SearchResultSimplifiedDTO;
import com.licenta.shmafaerserver.model.Recommendation;
import com.licenta.shmafaerserver.service.github.GithubSearchService;
import com.licenta.shmafaerserver.service.softwareheritage.ArchivingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationSystem {
    private final static Integer RECOMMENDATION_COUNT = 10;

    private final GithubSearchService githubSearchService;
    private final ArchivingService archivingService;

    public List<ItemSimplifiedDTO> getTextRelatedRecommendation(String text)
    {
        List<ItemSimplifiedDTO> recommendations = new ArrayList<>();
        Integer page = 1;
        Integer per_page = 10;


        while(recommendations.size() < RECOMMENDATION_COUNT)
        {
            recommendations.addAll(getPerPageRecommendation(text, per_page, page));
            page = page + 1;
        }

        return recommendations;

    }

    private List<ItemSimplifiedDTO> getPerPageRecommendation(String text, Integer per_page, Integer page)
    {

        List<ItemSimplifiedDTO> recommendations;
        SearchResultSimplifiedDTO githubResult = githubSearchService.searchRepos(text, per_page, page);

        //System.out.println("Inter: " + githubResult.getTotal_count());

        for(ItemSimplifiedDTO item: githubResult.getItems())
        {
            System.out.println(item.getHtml_url());
        }

        recommendations = githubResult.getItems().stream().filter(
                item -> archivingService.isArchivedBySH(item.getHtml_url())).collect(Collectors.toList());

        return recommendations;

    }
}
