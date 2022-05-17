package com.licenta.shmafaerserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
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
    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotBlank
    @Size(max = 100)
    @Column(unique = true)
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
    private Set<AppUser> collaborators = new HashSet<>();
}
