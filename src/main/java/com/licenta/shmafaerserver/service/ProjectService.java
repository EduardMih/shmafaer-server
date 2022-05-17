package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.converter.ProjectConverter;
import com.licenta.shmafaerserver.dto.request.AddProjectDTO;
import com.licenta.shmafaerserver.dto.response.GetProjectsResponseDTO;
import com.licenta.shmafaerserver.dto.response.ProjectDataDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.*;
import com.licenta.shmafaerserver.model.ProjectStatus;
import com.licenta.shmafaerserver.model.enums.EProjectStatus;
import com.licenta.shmafaerserver.model.enums.EProjectType;
import com.licenta.shmafaerserver.model.enums.ERole;
import com.licenta.shmafaerserver.model.Project;
import com.licenta.shmafaerserver.repository.ProjectRepository;
import com.licenta.shmafaerserver.repository.ProjectStatusRepository;
import com.licenta.shmafaerserver.repository.RoleRepository;
import com.licenta.shmafaerserver.security.IAuthenticationFacade;
import com.licenta.shmafaerserver.security.service.UserDetailsImpl;
import com.licenta.shmafaerserver.service.softwareheritage.ArchivingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Objects;


@Service @RequiredArgsConstructor @Transactional @Slf4j
public class ProjectService {
    private final ProjectConverter projectConverter;
    private final ProjectRepository projectRepository;
    private final RoleRepository roleRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final IAuthenticationFacade authenticationFacade;

    private final ArchivingService archivingService;

    public Project saveProject(AddProjectDTO newProject)
            throws UnknownProjectType, UnknownUserEmail, InvalidProjectStructure, ProjectLinkAlreadyExists
    {
        Project project;

        preValidateProjectDTO(newProject);
        project = projectConverter.convertAddProjectDTOToEntity(newProject);
        postValidateProject(project);

        return projectRepository.save(project);

    }

    private void preValidateProjectDTO(AddProjectDTO newProject) throws InvalidProjectStructure, ProjectLinkAlreadyExists
    {
        if(projectRepository.existsByRepoLink(newProject.getRepoLink()))
        {
            throw new ProjectLinkAlreadyExists();
        }
        if((Objects.equals(newProject.getProjectType(), EProjectType.BACHELOR.name())) ||
                (Objects.equals(newProject.getProjectType(), EProjectType.MASTERY.name())) ||
                (Objects.equals(newProject.getProjectType(), EProjectType.DOCTORAL.name())))
        {
            if((newProject.getCoordinatorEmail() == null) || (Objects.equals("", newProject.getCoordinatorEmail().trim())))
            {
                throw new InvalidProjectStructure("Project must have a coordinator");
            }

            if((newProject.getOwnerEmail() == null) || (Objects.equals("", newProject.getOwnerEmail().trim())))
            {
                throw new InvalidProjectStructure("Project must have a owner");
            }
        }

        else

        {
            if((newProject.getCollaboratorsEmail() == null) || (newProject.getCollaboratorsEmail().isEmpty()))
            {
                throw new InvalidProjectStructure("Academic research projects must have collaborators set");
            }

            //if((newProject.getOwnerEmail() != null) || (newProject.getCoordinatorEmail() != null))
            if(newProject.getCoordinatorEmail() != null)
            {
                throw new InvalidProjectStructure("Academic research projects can't have coordinator or owner");
            }
        }
    }

    private void postValidateProject(Project newProject) throws InvalidProjectStructure
    {
        if((Objects.equals(newProject.getProjectType().getName(), EProjectType.BACHELOR)) ||
                (Objects.equals(newProject.getProjectType().getName(), EProjectType.MASTERY)) ||
                (Objects.equals(newProject.getProjectType().getName(), EProjectType.DOCTORAL)))
        {
            if(!newProject.getCoordinator().getRoles().contains(roleRepository.findRoleByName(ERole.PROFESSOR))) {
                throw new InvalidProjectStructure("Project coordinator must have PROFESSOR role");
            }
        }
    }

    public GetProjectsResponseDTO getProjects(Pageable pageable)
    {
        Page<Project> projectsPage = projectRepository.findAll(pageable);

        return buildGetProjectsResponseDTO(projectsPage);

    }

    public GetProjectsResponseDTO getUserProjects(Pageable pageable)
    {
        UserDetailsImpl userDetails = authenticationFacade.getAuthenticatedUser();

        Page<Project> projectsPage = projectRepository.findDistinctByOwnerEmail(userDetails.getEmail(), pageable);

        return buildGetProjectsResponseDTO(projectsPage);

    }

    public GetProjectsResponseDTO getCoordinatedProjects(Pageable pageable)
    {
        UserDetailsImpl userDetails = authenticationFacade.getAuthenticatedUser();

        Page<Project> projectsPage = projectRepository.findDistinctByCoordinatorEmail(userDetails.getEmail(), pageable);

        return buildGetProjectsResponseDTO(projectsPage);

    }

    public GetProjectsResponseDTO getCollaboratedProjects(Pageable pageable)
    {
        UserDetailsImpl userDetails = authenticationFacade.getAuthenticatedUser();

        Page<Project> projectsPage = projectRepository.findDistinctByCollaboratorsEmail(userDetails.getEmail(), pageable);

        return buildGetProjectsResponseDTO(projectsPage);

    }

    public ProjectDataDTO updateArchivingStatus(String repoLink) throws UnknownProjectRepoLink, SoftwareHeritageCommunicationException
    {
        Project project = projectRepository.findByRepoLink(repoLink)
                .orElseThrow(() -> new UnknownProjectRepoLink(repoLink));


        EProjectStatus newStatusName = this.archivingService.getArchivingStatus(repoLink);

        if((newStatusName != null) && (newStatusName != project.getStatus().getName()))
        {
            project.setStatus(this.projectStatusRepository.findByName(newStatusName));
        }

        return projectConverter.convertEntityToProjectDataDTO(project);

    }

    private GetProjectsResponseDTO buildGetProjectsResponseDTO(Page<Project> projectsPage)
    {
        GetProjectsResponseDTO result = new GetProjectsResponseDTO(
                projectsPage.getTotalElements(), new ArrayList<>()
        );

        projectsPage.getContent().forEach(project -> {
            result.getProjects().add(projectConverter.convertEntityToProjectDataDTO(project));
        });

        return result;

    }
}
