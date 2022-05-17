package com.licenta.shmafaerserver.dto.softwareheritage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class DownloadResponse {
    private String fetch_url;
    private String progress_message;
    private String id;
    private String status;
    private String swhid;
}
