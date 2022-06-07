package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.dto.response.GetRecommendationsDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownUserEmail;
import com.licenta.shmafaerserver.service.recommendation.RecommendationService;
import com.licenta.shmafaerserver.service.recommendation.RecommendationServiceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationServiceFacade recommendationFacade;


    @GetMapping
    public ResponseEntity<Object> getUserRecommendation(@RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "3") int size)
            throws UnknownUserEmail
    {
        Pageable pageable = PageRequest.of(page, size);
        GetRecommendationsDTO response = recommendationFacade.getUserRecommendations(pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
