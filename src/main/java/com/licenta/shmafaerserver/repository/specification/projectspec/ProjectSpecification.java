package com.licenta.shmafaerserver.repository.specification.projectspec;

import com.licenta.shmafaerserver.model.Project;
import com.licenta.shmafaerserver.model.enums.EProjectType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class ProjectSpecification {
    public static Specification<Project> titleContains(String titlePattern)
    {

        return (project, cq, cb) -> {

            if (titlePattern == null)
            {

                return cb.conjunction();

            }

            return cb.like(project.get("title"), "%" + titlePattern + "%");

        };

    }

    public static Specification<Project> hasCollaborator(String collaboratorEmail)
    {

        return (project, cq, cb) -> {

            Join<Object, Object> joined = project.join("collaborators", JoinType.INNER);
            cq.distinct(true);

            if(collaboratorEmail == null)
            {

                return cb.conjunction();

            }

            //return cb.equal(project.join("collaborators", JoinType.INNER).get("email"), collaboratorEmail);

            return cb.equal(joined.get("email"), collaboratorEmail);
        };


    }

    public static Specification<Project> hasOwner(String contributorEmail)
    {

        return (project, cq, cb) -> {

            //project.join("collaborators", JoinType.INNER);

            if(contributorEmail == null)
            {

                return cb.conjunction();

            }

            return cb.equal(project.get("owner").get("email"), contributorEmail);

        };
    }

    public static Specification<Project> hasCoordinator(String coordinatorEmail)
    {

        return (project, cq, cb) -> {

            //project.join("collaborators", JoinType.INNER);

            if(coordinatorEmail == null)
            {

                return cb.conjunction();

            }

            return cb.equal(project.get("coordinator").get("email"), coordinatorEmail);

        };
    }

    public static Specification<Project> hasType(EProjectType projectType)
    {

        return (project, cq, cb) -> {

            if(projectType == null)
            {

                return cb.conjunction();

            }

            return cb.equal(project.get("projectType").get("name"), projectType);

        };

    }
}
