package com.licenta.shmafaerserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class ProjectDataDTO {
    private String title;
    private String repoLink;
    private String description;
    private String projectType;
    private String status;

    private MinimalUserDetailsDTO owner;
    private MinimalUserDetailsDTO coordinator;
    private List<MinimalUserDetailsDTO> collaborators;



}
