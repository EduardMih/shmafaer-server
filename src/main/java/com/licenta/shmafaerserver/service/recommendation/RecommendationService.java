package com.licenta.shmafaerserver.service.recommendation;

import com.licenta.shmafaerserver.converter.RecommendationConverter;
import com.licenta.shmafaerserver.dto.github.ItemSimplifiedDTO;
import com.licenta.shmafaerserver.dto.github.SearchResultSimplifiedDTO;
import com.licenta.shmafaerserver.dto.response.GetRecommendationsDTO;
import com.licenta.shmafaerserver.dto.response.RecommendationDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownUserEmail;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.Recommendation;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import com.licenta.shmafaerserver.repository.RecommendationRepository;
import com.licenta.shmafaerserver.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private static final int MAX_USER_RECOMMENDATIONS_COUNT = 20;

    private final RecommendationSystem recommendationSystem;
    private final RecommendationRepository recommendationRepository;
    private final RecommendationConverter recommendationConverter;

    public void createRecommendations(AppUser user, String text)
    {
        List<ItemSimplifiedDTO> items = recommendationSystem.getTextRelatedRecommendation(text);
        List<Recommendation> result = new ArrayList<>();
        Recommendation temp;

        for(ItemSimplifiedDTO item: items)
        {
            temp = recommendationConverter.convertItemsToEntity(item);
            temp.setUser(user);
            temp.setRecommendedFor(text);
            System.out.println(temp.getDescription());

            result.add(temp);
        }
        recommendationRepository.saveAll(result);
        clearOldRecommendations(user);
    }

    public GetRecommendationsDTO getRecommendations(AppUser user, Pageable page)
    {
        /*
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        AppUser user = userRepository.findAppUserByEmail(userDetails.getEmail()).orElseThrow(UnknownUserEmail::new);

         */

        Page<Recommendation> recommendationPage = recommendationRepository.findAllByUserOrderByCreatedAtDesc(user, page);
        List<Recommendation> result = recommendationPage.getContent();
        GetRecommendationsDTO response = new GetRecommendationsDTO();
        List<RecommendationDTO> recommendations = result.stream()
                .map(recommendationConverter::convertEntityToRecommendationDTO).collect(Collectors.toList());

        response.setNrOfRecommendations(recommendationPage.getTotalElements());
        response.setRecommendations(recommendations);

        return response;

    }

    public void clearOldRecommendations(AppUser user)
    {
        Long count = recommendationRepository.countByUser(user);
        List<Recommendation> toBeDeleted;

        if(count > MAX_USER_RECOMMENDATIONS_COUNT)
        {
            toBeDeleted = recommendationRepository.findTop10ByUserOrderByCreatedAtAsc(user);
            recommendationRepository.deleteAll(toBeDeleted);
        }
    }
}
