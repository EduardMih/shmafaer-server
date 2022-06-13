package com.licenta.shmafaerserver.service;

import com.licenta.shmafaerserver.dto.response.ProjectTypesStatsDTO;
import com.licenta.shmafaerserver.model.enums.EProjectStatus;
import com.licenta.shmafaerserver.model.enums.EProjectType;
import com.licenta.shmafaerserver.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectsStatsService {
    private final ProjectRepository projectRepository;

    public ProjectTypesStatsDTO getProjectsTypeStats()
    {
        ProjectTypesStatsDTO stats = new ProjectTypesStatsDTO();
        Long bachelorCount = projectRepository.countByProjectTypeName(EProjectType.BACHELOR);
        Long masteryCount = projectRepository.countByProjectTypeName(EProjectType.MASTERY);
        Long doctoralCount = projectRepository.countByProjectTypeName(EProjectType.DOCTORAL);
        Long researchCount = projectRepository.countByProjectTypeName(EProjectType.RESEARCH);

        stats.setBachelorCount(bachelorCount);
        stats.setMasteryCount(masteryCount);
        stats.setDoctoralCount(doctoralCount);
        stats.setResearchCount(researchCount);
        stats.setTotal(bachelorCount + masteryCount + doctoralCount + researchCount);


        return stats;

    }

    public ProjectTypesStatsDTO getProjectsArchivedStats()
    {
        ProjectTypesStatsDTO stats = getProjectsTypeStats();
        Long bachelorArchivedCount = projectRepository.countByProjectTypeNameAndStatusName(EProjectType.BACHELOR, EProjectStatus.SUCCEEDED);
        Long masteryArchivedCount = projectRepository.countByProjectTypeNameAndStatusName(EProjectType.MASTERY, EProjectStatus.SUCCEEDED);
        Long doctoralArchivedCount = projectRepository.countByProjectTypeNameAndStatusName(EProjectType.DOCTORAL, EProjectStatus.SUCCEEDED);
        Long researchArchivedCount = projectRepository.countByProjectTypeNameAndStatusName(EProjectType.RESEARCH, EProjectStatus.SUCCEEDED);

        stats.setBachelorArchivedCount(bachelorArchivedCount);
        stats.setMasteryArchivedCount(masteryArchivedCount);
        stats.setDoctoralArchivedCount(doctoralArchivedCount);
        stats.setResearchArchivedCount(researchArchivedCount);
        stats.setTotalArchived(bachelorArchivedCount + masteryArchivedCount + doctoralArchivedCount + researchArchivedCount);


        return stats;

    }
}
