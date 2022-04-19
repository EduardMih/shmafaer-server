package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.dto.requests.AddProjectDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidProjectStructure;
import com.licenta.shmafaerserver.exception.CustomExceptions.ProjectLinkAlreadyExists;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownProjectType;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownUserEmail;
import com.licenta.shmafaerserver.model.Project;
import com.licenta.shmafaerserver.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping()
    public ResponseEntity<Object> addProject(@Valid @RequestBody AddProjectDTO newProject)
            throws UnknownProjectType, UnknownUserEmail, InvalidProjectStructure, ProjectLinkAlreadyExists
    {
        projectService.saveProject(newProject);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
