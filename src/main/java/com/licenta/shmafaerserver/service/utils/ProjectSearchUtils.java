package com.licenta.shmafaerserver.service.utils;

import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownProjectType;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownUserEmail;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.Project;
import com.licenta.shmafaerserver.model.ProjectType;
import com.licenta.shmafaerserver.model.enums.EProjectType;
import com.licenta.shmafaerserver.repository.AppUserRepository;
import com.licenta.shmafaerserver.repository.ProjectTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProjectSearchUtils {
    private final ProjectTypeRepository projectTypeRepository;
    private final AppUserRepository userRepository;

    public Project buildProjectToSearch(String titlePattern, String coordinatorEmail,
                                        String contributorEmail, String projectType)
            throws UnknownProjectType, UnknownUserEmail
    {
        Project projectToSearch = new Project();
        Example<Project> example;

        setType(projectToSearch, projectType);

        if((projectToSearch.getProjectType() != null) && (projectToSearch.getProjectType().getName() == EProjectType.RESEARCH))
        {
            populateResearchProject(projectToSearch, contributorEmail);
        }

        else

        {
            populateSchoolProject(projectToSearch, coordinatorEmail, contributorEmail);
        }

        if(titlePattern != null)
        {
            projectToSearch.setTitle(titlePattern);
        }

        return projectToSearch;

    }

    private void setType(Project projectToSearch, String projectType) throws UnknownProjectType
    {
        ProjectType type;

        if(!Objects.equals(projectType, "ALL"))
        {
            try
            {
                type = projectTypeRepository.findByName(EProjectType.valueOf(projectType))
                        .orElseThrow(UnknownProjectType::new);
                projectToSearch.setProjectType(type);
            }
            catch (UnknownProjectType | IllegalArgumentException e)
            {
                throw new UnknownProjectType();
            }
        }
    }

    private void populateResearchProject(Project projectToSearch, String contributorEmail) throws UnknownUserEmail
    {
        AppUser contributor;

        if(contributorEmail != null)
        {
            contributor = userRepository.findAppUserByEmail(contributorEmail)
                    .orElseThrow(UnknownUserEmail::new);
            projectToSearch.setCollaborators(Set.of(contributor));
        }
    }

    private void populateSchoolProject(Project projectToSearch, String coordinatorEmail, String contributorEmail)
            throws UnknownUserEmail
    {
        AppUser contributor, coordinator;

        if(contributorEmail != null)
        {
            contributor = userRepository.findAppUserByEmail(contributorEmail)
                    .orElseThrow(UnknownUserEmail::new);
            projectToSearch.setOwner(contributor);
        }

        if(coordinatorEmail != null)
        {
            coordinator = userRepository.findAppUserByEmail(coordinatorEmail)
                    .orElseThrow(UnknownUserEmail::new);
            projectToSearch.setCoordinator(coordinator);
        }
    }

    public Example<Project> getExampleOf(Project projectToSearch)
    {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withMatcher("title",
                ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        return Example.of(projectToSearch, exampleMatcher);

    }


}
