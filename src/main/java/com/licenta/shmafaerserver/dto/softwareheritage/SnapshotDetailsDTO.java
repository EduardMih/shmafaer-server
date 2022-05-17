package com.licenta.shmafaerserver.dto.softwareheritage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class SnapshotDetailsDTO {
    private String id;
    LinkedHashMap<String, RevisionDataDTO> branches;

}
