package com.licenta.shmafaerserver.converter;

import com.licenta.shmafaerserver.dto.requests.AddProjectDTO;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownProjectType;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownUserEmail;
import com.licenta.shmafaerserver.model.EProjectStatus;
import com.licenta.shmafaerserver.model.EProjectType;
import com.licenta.shmafaerserver.model.Project;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import com.licenta.shmafaerserver.repository.ProjectStatusRepository;
import com.licenta.shmafaerserver.repository.ProjectTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class ProjectConverter {
    private final ModelMapper modelMapper;
    private final ProjectStatusRepository projectStatusRepository;
    private final ProjectTypeRepository projectTypeRepository;
    private final AppUserRepository userRepository;


    public Project convertAddProjectDTOToEntity(AddProjectDTO newProject) throws UnknownProjectType, UnknownUserEmail
    {
        Project project;
        EProjectType type;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        project = modelMapper.map(newProject, Project.class);

        //initially a new project is just saved locally
        project.setStatus(projectStatusRepository.findByName(EProjectStatus.SAVED));

        try
        {
            type = EProjectType.valueOf(newProject.getProjectType());
            project.setProjectType(projectTypeRepository.findByName(type)
                    .orElseThrow(UnknownProjectType::new));
        }
        catch (IllegalArgumentException e)
        {
            throw new UnknownProjectType();
        }



        if(newProject.getOwnerEmail() != null)
        {
            project.setOwner(userRepository.findAppUserByEmail(newProject.getOwnerEmail())
                    .orElseThrow(UnknownUserEmail::new));
        }

        if(newProject.getCoordinatorEmail() != null)
        {
            project.setCoordinator(userRepository.findAppUserByEmail(newProject.getCoordinatorEmail())
                    .orElseThrow(UnknownUserEmail::new));
        }

        if(newProject.getCollaboratorsEmail() != null)
        {
            for(String collaboratorEmail: newProject.getCollaboratorsEmail())
            {
                project.getCollaborators().add(userRepository.findAppUserByEmail(collaboratorEmail)
                        .orElseThrow(UnknownUserEmail::new));

            }
        }

        return project;

    }
}
