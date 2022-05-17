package com.licenta.shmafaerserver.controller;

import com.licenta.shmafaerserver.dto.request.AddProjectDTO;
import com.licenta.shmafaerserver.dto.response.DownloadResponseDTO;
import com.licenta.shmafaerserver.dto.response.GetProjectsResponseDTO;
import com.licenta.shmafaerserver.dto.response.SuccessResponse;
import com.licenta.shmafaerserver.exception.CustomExceptions.*;
import com.licenta.shmafaerserver.service.ProjectService;
import com.licenta.shmafaerserver.service.softwareheritage.ArchivingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ArchivingService archivingService;

    @PostMapping()
    public ResponseEntity<Object> addProject(@Valid @RequestBody AddProjectDTO newProject)
            throws UnknownProjectType, UnknownUserEmail, InvalidProjectStructure, ProjectLinkAlreadyExists
    {
        projectService.saveProject(newProject);

        return new ResponseEntity<>(new SuccessResponse("Project created successfully"), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<Object> getProject(@RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "size", defaultValue = "3") int size)
    {

        Pageable pageable = PageRequest.of(page, size);
        GetProjectsResponseDTO result = projectService.getProjects(pageable);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //by default owned projects if no other flags are specified
    @GetMapping("/userProjects")
    public ResponseEntity<Object> getUserProjects(@RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "size", defaultValue = "3") int size,
                                                  @RequestParam(name = "coordinated", defaultValue = "false", required = false) boolean coordinated,
                                                  @RequestParam(name = "collaborated", defaultValue = "false", required = false) boolean collaborated)
    {
        Pageable pageable = PageRequest.of(page, size);
        GetProjectsResponseDTO result;

        if(coordinated)
        {
            result = projectService.getCoordinatedProjects(pageable);
        }

        else
        {

            if (collaborated)
            {
                result = projectService.getCollaboratedProjects(pageable);
            }

            else

            {

                result = projectService.getUserProjects(pageable);

            }

        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/archive")
    public ResponseEntity<Object> getArchivingStatus(@RequestParam("projectRepoLink") String projectRepoLink)
            throws UnknownProjectRepoLink, SoftwareHeritageCommunicationException
    {

      return new ResponseEntity<>(projectService.updateArchivingStatus(projectRepoLink), HttpStatus.OK);

    }

    @GetMapping("/archive/download")
    public ResponseEntity<Object> downloadProject(@RequestParam String projectRepoLink) throws SoftwareHeritageCommunicationException
    {
        DownloadResponseDTO response = archivingService.getDownloadInfo(projectRepoLink);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
