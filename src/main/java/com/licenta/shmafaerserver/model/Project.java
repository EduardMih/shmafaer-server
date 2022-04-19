package com.licenta.shmafaerserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String title;

    @Size(max = 500)
    private String description;

    @NotBlank
    @Size(max = 100)
    private String repoLink;

    @ManyToOne
    private ProjectType projectType;

    @ManyToOne
    private ProjectStatus status;

    @ManyToOne (fetch = FetchType.EAGER)
    private AppUser owner;

    @ManyToOne
    private AppUser coordinator;

    @ManyToMany (fetch = FetchType.EAGER)
    private Set<AppUser> collaborators;
}
