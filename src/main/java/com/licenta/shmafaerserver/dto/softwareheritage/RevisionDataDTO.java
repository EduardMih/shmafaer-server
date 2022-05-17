package com.licenta.shmafaerserver.dto.softwareheritage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class RevisionDataDTO {
    private String target;
    private String target_type;
    private String target_url;
}
