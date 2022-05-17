package com.licenta.shmafaerserver.dto.softwareheritage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class LatestVisitDTO {
    private String origin;
    private Long visit;
    private String date;
    private String status;
    private String snapshot;
    private String type;
    private Object metadata;
    private String origin_url;
    private String snapshot_url;
}
