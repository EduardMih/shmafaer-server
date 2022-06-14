package com.licenta.shmafaerserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AppUser user;

    private String name;
    private String htmlUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String stargazersCount;
    private String language;
    private String SHBrowseUrl;

    @Column(columnDefinition = "TEXT")
    private String topics;

    private String recommendedFor;
    private Date createdAt;

}
