package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.dto.request.AddRatingDTO;
import com.licenta.shmafaerserver.dto.response.GetProjectRatingDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownProjectID;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.Project;
import com.licenta.shmafaerserver.model.Rating;
import com.licenta.shmafaerserver.model.RatingId;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import com.licenta.shmafaerserver.repository.ProjectRepository;
import com.licenta.shmafaerserver.repository.RatingRepository;
import com.licenta.shmafaerserver.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final ProjectRepository projectRepository;
    private final AppUserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    public GetProjectRatingDTO getProjectRating(Long projectID)
    {

        return new GetProjectRatingDTO(projectID,
                ratingRepository.getAverageProjectRating(projectID).orElse(0.0)
        );

    }


    public GetProjectRatingDTO addProjectRating(AddRatingDTO ratingDTO) throws UnknownProjectID
    {
        Project project = projectRepository.findById(ratingDTO.getProjectID())
                .orElseThrow(() -> new UnknownProjectID(Long.toString(ratingDTO.getProjectID())));

        AppUser currentUser = userRepository.getById(authenticationFacade.getAuthenticatedUser().getId());
        Rating newRating = new Rating(new RatingId(currentUser, project), ratingDTO.getRatingValue());

        ratingRepository.save(newRating);


        // return new rating
        return getProjectRating(ratingDTO.getProjectID());

    }

}
