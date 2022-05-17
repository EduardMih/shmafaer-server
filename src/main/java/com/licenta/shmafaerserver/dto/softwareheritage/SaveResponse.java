package com.licenta.shmafaerserver.dto.softwareheritage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class SaveResponse {
    private String origin_url;
    private String visit_type;
    private String save_request_date;
    private String save_request_status;
    private String save_task_status;
    private String visit_date;
    private String visit_status;
    private String note;
}
