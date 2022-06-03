package com.licenta.shmafaerserver.service.utils;

import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownProjectType;
import com.licenta.shmafaerserver.model.Project;
import com.licenta.shmafaerserver.model.ProjectType;
import com.licenta.shmafaerserver.model.enums.EProjectType;
import com.licenta.shmafaerserver.repository.ProjectTypeRepository;
import com.licenta.shmafaerserver.repository.specification.projectspec.ProjectSpecification;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProjectSearchUtils {
    private final ProjectTypeRepository projectTypeRepository;

    public static Specification<Project> buildSchoolProjectSpec(String titlePattern, String contributorEmail,
                                                         String coordinatorEmail, EProjectType projectType)
    {

        Specification<Project> specifications = Specification.where(ProjectSpecification.titleContains(titlePattern));
        specifications = specifications.and(ProjectSpecification.hasType(projectType));

        specifications = specifications.and(ProjectSpecification.hasCoordinator(coordinatorEmail));
        specifications = specifications.and(ProjectSpecification.hasOwner(contributorEmail));

        return Specification.where(specifications);

    }

    public static Specification<Project> buildResearchProjectSpec(String titlePattern, String contributorEmail)
    {

        Specification<Project> specifications = Specification.where(ProjectSpecification.titleContains(titlePattern));
        specifications = specifications.and(ProjectSpecification.hasType(EProjectType.RESEARCH));
        specifications = specifications.and(ProjectSpecification.hasCollaborator(contributorEmail));

        return Specification.where(specifications);

    }

    public EProjectType getTypeName(String projectType) throws UnknownProjectType
    {
        ProjectType type;

        if(!Objects.equals(projectType, "ALL"))
        {
            try
            {
                type = projectTypeRepository.findByName(EProjectType.valueOf(projectType))
                        .orElseThrow(UnknownProjectType::new);

                return type.getName();

            }
            catch (UnknownProjectType | IllegalArgumentException e)
            {
                throw new UnknownProjectType();
            }
        }

        return null;

    }

}
