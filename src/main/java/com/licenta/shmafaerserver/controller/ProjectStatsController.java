package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.dto.response.ProjectTypesStatsDTO;
import com.licenta.shmafaerserver.service.ProjectsStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/projectsStats")
@RequiredArgsConstructor
public class ProjectStatsController {
    private final ProjectsStatsService projectsStatsService;

    @GetMapping("/types")
    public ResponseEntity<Object> getProjectTypesStats()
    {
        ProjectTypesStatsDTO stats = projectsStatsService.getProjectsTypeStats();

        return new ResponseEntity<>(stats, HttpStatus.OK);

    }

    @GetMapping("/archived")
    public ResponseEntity<Object> getArchivedStats()
    {
        ProjectTypesStatsDTO stats = projectsStatsService.getProjectsArchivedStats();

        return new ResponseEntity<>(stats, HttpStatus.OK);

    }
}
