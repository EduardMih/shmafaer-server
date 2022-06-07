package com.licenta.shmafaerserver.dto.github;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class SearchResultSimplifiedDTO {
    private Long total_count;
    List<ItemSimplifiedDTO> items;
}
