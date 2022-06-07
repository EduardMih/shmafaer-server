package com.licenta.shmafaerserver.dto.github;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class ItemSimplifiedDTO {
    private String name;
    private String html_url;
    private String description;
    private String stargazers_count;
    private String language;
    private List<String> topics;
}
