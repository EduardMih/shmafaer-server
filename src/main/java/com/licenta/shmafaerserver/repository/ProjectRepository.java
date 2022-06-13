package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.Project;
import com.licenta.shmafaerserver.model.enums.EProjectStatus;
import com.licenta.shmafaerserver.model.enums.EProjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    boolean existsByRepoLink(String link);
    Optional<Project> findByRepoLink(String repoLink);
    Optional<Project> findById(Long id);
    Page<Project> findAll(Pageable pageable);
    Page<Project> findDistinctByOwnerEmail(String email, Pageable pageable);
    Page<Project> findDistinctByCoordinatorEmail(String email, Pageable pageable);
    Page<Project> findDistinctByCollaboratorsEmail(String email, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE (:title is null OR p.title LIKE %:title%) " +
            "AND (:collaborator is null OR :collaborator member p.collaborators OR p.owner = :collaborator) " +
            "AND (:projectType is null OR p.projectType.name = :projectType)" +
            "AND (:coordinator is null OR p.coordinator = :coordinator)")
    Page<Project> findThat(@Param("collaborator") AppUser collaborator,
                           @Param("coordinator") AppUser coordinator,
                           @Param("title") String title,
                           @Param("projectType") EProjectType projectType,
                           Pageable pageable);

    Long countByProjectTypeName(EProjectType projectType);
    Long countByProjectTypeNameAndStatusName(EProjectType projectType, EProjectStatus status);


}
