package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.EProjectStatus;
import com.licenta.shmafaerserver.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
    ProjectStatus findByName(EProjectStatus name);
}
