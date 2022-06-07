package com.licenta.shmafaerserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class RecommendationDTO {
    private String name;
    private String htmlUrl;
    private String description;
    private String stargazersCount;
    private String language;
    private String shBrowseUrl;
    private List<String> topics;
    private String recommendedFor;
}
