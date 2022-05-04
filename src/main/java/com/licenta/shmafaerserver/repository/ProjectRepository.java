package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByRepoLink(String link);
    Page<Project> findAll(Pageable pageable);
    Page<Project> findDistinctByOwnerEmail(String email, Pageable pageable);
    Page<Project> findDistinctByCoordinatorEmail(String email, Pageable pageable);
    Page<Project> findDistinctByCollaboratorsEmail(String email, Pageable pageable);


}
