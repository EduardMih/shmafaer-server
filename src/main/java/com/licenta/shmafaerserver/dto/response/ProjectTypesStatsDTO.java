package com.licenta.shmafaerserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class ProjectTypesStatsDTO {
    private Long total;
    private Long totalArchived;
    private Long bachelorCount;
    private Long bachelorArchivedCount;
    private Long masteryCount;
    private Long masteryArchivedCount;
    private Long doctoralCount;
    private Long doctoralArchivedCount;
    private Long researchCount;
    private Long researchArchivedCount;
}
