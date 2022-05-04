package com.licenta.shmafaerserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class GetProjectsResponseDTO {
    private long nrOfProjects;
    private List<ProjectDataDTO> projects;
}
