package com.licenta.shmafaerserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class RegisterResponseDTO {
    private String firstname;
    private String lastname;
    private String message;
}
