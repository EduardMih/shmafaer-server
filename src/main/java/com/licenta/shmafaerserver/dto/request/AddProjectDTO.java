package com.licenta.shmafaerserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class AddProjectDTO {
    @NotBlank(message = "Title is mandatory!")
    @Size(max = 50)
    private String title;

    @Size(max = 500)
    private String description;

    @NotBlank(message = "Repository link is mandatory!")
    @Size(max = 100)
    private String repoLink;

    private String projectType;

    //private String ownerEmail;

    private String coordinatorEmail;

    private Set<String> collaboratorsEmail;
}
