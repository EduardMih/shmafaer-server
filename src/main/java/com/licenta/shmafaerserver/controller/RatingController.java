package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.dto.request.AddRatingDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownProjectID;
import com.licenta.shmafaerserver.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
@Slf4j
public class RatingController {
    private final RatingService ratingService;

    @GetMapping()
    public ResponseEntity<Object> getProjectRating(@RequestParam(name = "projectID") Long projectID)
    {

        return new ResponseEntity<>(ratingService.getProjectRating(projectID), HttpStatus.OK);

    }

    @PostMapping()
    public ResponseEntity<Object> addProjectRating(@Valid @RequestBody AddRatingDTO ratingDTO)
            throws UnknownProjectID
    {

        return new ResponseEntity<>(ratingService.addProjectRating(ratingDTO), HttpStatus.OK);

    }
}
