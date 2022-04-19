package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.EProjectType;
import com.licenta.shmafaerserver.model.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {
    Optional<ProjectType> findByName(EProjectType name);
}
