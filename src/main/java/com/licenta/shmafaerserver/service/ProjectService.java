package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.converter.ProjectConverter;
import com.licenta.shmafaerserver.dto.request.AddProjectDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.InvalidProjectStructure;
import com.licenta.shmafaerserver.exception.CustomExceptions.ProjectLinkAlreadyExists;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownProjectType;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownUserEmail;
import com.licenta.shmafaerserver.model.enums.EProjectType;
import com.licenta.shmafaerserver.model.enums.ERole;
import com.licenta.shmafaerserver.model.Project;
import com.licenta.shmafaerserver.repository.ProjectRepository;
import com.licenta.shmafaerserver.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;


@Service @RequiredArgsConstructor @Transactional @Slf4j
public class ProjectService {
    private final ProjectConverter projectConverter;
    private final ProjectRepository projectRepository;
    private final RoleRepository roleRepository;

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

}
