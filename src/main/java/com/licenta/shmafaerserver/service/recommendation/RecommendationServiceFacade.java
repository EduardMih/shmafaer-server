package com.licenta.shmafaerserver.service.recommendation;

import com.licenta.shmafaerserver.dto.response.GetRecommendationsDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownUserEmail;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import com.licenta.shmafaerserver.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendationServiceFacade {
    private final RecommendationService recommendationService;
    private final RecommendationTaskService recommendationTaskService;
    private final AppUserRepository userRepository;

    public void createRecommendationTask(String text) throws UnknownUserEmail
    {
        AppUser user = getCurrentAuthenticatedUser();

        recommendationTaskService.createTask(user, text);
    }

    public GetRecommendationsDTO getUserRecommendations(Pageable pageable) throws UnknownUserEmail
    {
        AppUser user = getCurrentAuthenticatedUser();

        return recommendationService.getRecommendations(user, pageable);

    }

    private AppUser getCurrentAuthenticatedUser() throws UnknownUserEmail
    {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return  userRepository.findAppUserByEmail(userDetails.getEmail()).orElseThrow(UnknownUserEmail::new);

    }
}
