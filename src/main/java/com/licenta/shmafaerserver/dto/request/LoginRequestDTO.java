package com.licenta.shmafaerserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class LoginRequestDTO {
    @Size(max = 120)
    private String email; // email
    @Size(max = 120)
    private String password;
}
