package com.licenta.shmafaerserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class MinimalUserDetailsDTO {
    private String firstname;
    private String lastname;
    private String email;
}
